package de.nkilders.compiler.parser;

import static de.nkilders.compiler.TokenType.DIVIDE;
import static de.nkilders.compiler.TokenType.EOF;
import static de.nkilders.compiler.TokenType.LINE_COMMENT;
import static de.nkilders.compiler.TokenType.LPAREN;
import static de.nkilders.compiler.TokenType.MINUS;
import static de.nkilders.compiler.TokenType.MULTIPLY;
import static de.nkilders.compiler.TokenType.MULTI_LINE_COMMENT;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.PLUS;
import static de.nkilders.compiler.TokenType.RPAREN;
import static de.nkilders.compiler.TokenType.WHITESPACE;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.CompilerException;
import de.nkilders.compiler.Token;
import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.parser.ast.BinaryExprNode;
import de.nkilders.compiler.parser.ast.BooleanExprNode;
import de.nkilders.compiler.parser.ast.ExprNode;
import de.nkilders.compiler.parser.ast.NumericExprNode;
import de.nkilders.compiler.parser.ast.RootNode;
import de.nkilders.compiler.parser.ast.StmtNode;
import de.nkilders.compiler.parser.ast.StringExprNode;
import de.nkilders.compiler.parser.ast.UnaryExprNode;
import de.nkilders.compiler.parser.ast.VarExprNode;

public class ParserImpl implements Parser {
    private List<Token> tokens;

    @Override
    public RootNode parse(List<Token> tokens) {
        this.tokens = tokens;
        cleanUpTokens();

        RootNode root = new RootNode();

        while(current().type() != EOF) {
            root.addStatement(parseStmt());
        }

        return root;
    }

    /**
     * Removes tokens which aren't needed for parsing
     */
    private void cleanUpTokens() {
        List<TokenType> ignoredTypes = List.of(
            WHITESPACE,
            LINE_COMMENT,
            MULTI_LINE_COMMENT
        );
            
        tokens = new ArrayList<>(tokens.stream().filter(t -> !ignoredTypes.contains(t.type())).toList());
    }

    /**
     * @return the current token without removing it from the tokens list
     */
    private Token current() {
        return tokens.get(0);
    }

    /**
     * @return the current token and removes it from the tokens list
     */
    private Token advance() {
        return tokens.remove(0);
    }

    /**
     * Checks whether the current token's type matches {@code expectedType}.
     * If yes, calls {@link ParserImpl#advance()} to remove current token from tokens list.
     * Otherwise throws an exception.
     * 
     * @param expectedType expected token type
     * @throws CompilerException if the current token's type doesn't match {@code expectedType}
     */
    private void expect(TokenType expectedType) throws CompilerException {
        TokenType actualType = current().type();

        if(actualType != expectedType) {
            String message = String.format("Expected %s token but found %s", expectedType, actualType);
            throw new CompilerException(message, current().pos());
        }

        advance();
    }

    // Stmt -> Expr
    private StmtNode parseStmt() {
        return parseExpr();
    }

    // Expr -> AddExpr
    private ExprNode parseExpr() {
        return parseAddExpr();
    }

    // AddExpr -> MulExpr ( ( PLUS | MINUS ) MulExpr )*
    private ExprNode parseAddExpr() {
        ExprNode left = parseMulExpr();

        while(isAddOperator(current())) {
            Token operator = advance();
            ExprNode right = parseMulExpr();

            left = new BinaryExprNode(left, right, operator.type());
        }

        return left;
    }

    private boolean isAddOperator(Token token) {
        return token.type() == PLUS || token.type() == MINUS;
    }
    
    // MulExpr -> UnaryExpr ( ( MULTIPLY | DIVIDE ) UnaryExpr )*
    private ExprNode parseMulExpr() {
        ExprNode left = parseUnaryExpr();

        while(isMulOperator(current())) {
            Token operator = advance();
            ExprNode right = parseUnaryExpr();

            left = new BinaryExprNode(left, right, operator.type());
        }

        return left;
    }

    private boolean isMulOperator(Token token) {
        return token.type() == MULTIPLY || token.type() == DIVIDE;
    }

    // UnaryExpr -> ( ( PLUS | MINUS | NOT ) UnaryExpr ) | PrimaryExpr
    private ExprNode parseUnaryExpr() {
        if(isUnaryOperator(current())) {
            return new UnaryExprNode(advance().type(), parseUnaryExpr());
        }

        return parsePrimaryExpr();
    }

    private boolean isUnaryOperator(Token token) {
        return token.type() == PLUS || token.type() == MINUS || token.type() == NOT;
    }
    
    // PrimaryExpr -> NUMBER | IDENTIFIER | ParenExpr
    private ExprNode parsePrimaryExpr() throws CompilerException {
        Token t = current();

        return switch (t.type()) {
            case NUMBER -> new NumericExprNode(Double.parseDouble(advance().content()));
            case IDENTIFIER -> new VarExprNode(advance().content());
            case TRUE, FALSE -> new BooleanExprNode(Boolean.parseBoolean(advance().content()));
            case STRING -> parseString(advance().content());
            case LPAREN -> parseParenExpr();
            default -> {
                String message = String.format("Unexpected token of type %s", t.type());
                throw new CompilerException(message, t.pos());
            }
        };
    }

    private StringExprNode parseString(String s) {
        s = s.substring(1, s.length() - 1);
        return new StringExprNode(s);
    }

    // ParenExpr -> LPAREN Expr RPAREN
    private ExprNode parseParenExpr() {
        expect(LPAREN);
        ExprNode expr = parseExpr();
        expect(RPAREN);

        return expr;
    }
}
