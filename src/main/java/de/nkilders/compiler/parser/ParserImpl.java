package de.nkilders.compiler.parser;

import static de.nkilders.compiler.TokenType.ASSIGN;
import static de.nkilders.compiler.TokenType.CONST;
import static de.nkilders.compiler.TokenType.DIVIDE;
import static de.nkilders.compiler.TokenType.EOF;
import static de.nkilders.compiler.TokenType.IDENTIFIER;
import static de.nkilders.compiler.TokenType.LBRACE;
import static de.nkilders.compiler.TokenType.LET;
import static de.nkilders.compiler.TokenType.LINE_COMMENT;
import static de.nkilders.compiler.TokenType.LPAREN;
import static de.nkilders.compiler.TokenType.MINUS;
import static de.nkilders.compiler.TokenType.MULTIPLY;
import static de.nkilders.compiler.TokenType.MULTI_LINE_COMMENT;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.PLUS;
import static de.nkilders.compiler.TokenType.RBRACE;
import static de.nkilders.compiler.TokenType.RPAREN;
import static de.nkilders.compiler.TokenType.WHITESPACE;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.CompilerException;
import de.nkilders.compiler.Token;
import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.parser.ast.AssignExprNode;
import de.nkilders.compiler.parser.ast.BinaryExprNode;
import de.nkilders.compiler.parser.ast.BlockStmtNode;
import de.nkilders.compiler.parser.ast.DeclareStmtNode;
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

    while (current().type() != EOF) {
      root.addStatement(parseStmt());
    }

    return root;
  }

  /**
   * Removes tokens which aren't needed for parsing
   */
  private void cleanUpTokens() {
    List<TokenType> ignoredTypes = List.of(WHITESPACE, LINE_COMMENT, MULTI_LINE_COMMENT);

    tokens = new ArrayList<>(tokens.stream()
        .filter(t -> !ignoredTypes.contains(t.type()))
        .toList());
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
   * Checks whether the current token's type matches {@code expectedType}. If yes,
   * calls {@link ParserImpl#advance()} to remove current token from tokens list.
   * Otherwise throws an exception.
   * 
   * @param expectedType expected token type
   * @return the current token
   * @throws CompilerException if the current token's type doesn't match
   *                           {@code expectedType}
   */
  private Token expect(TokenType expectedType) throws CompilerException {
    TokenType actualType = current().type();

    if (actualType != expectedType) {
      String message = String.format("Expected %s token but found %s", expectedType, actualType);
      throw new CompilerException(message, current().location());
    }

    return advance();
  }

  // Stmt -> BlockStmt | DeclareStmt | Expr
  private StmtNode parseStmt() {
    return switch (current().type()) {
      case LBRACE -> parseBlockStmt();
      case LET, CONST -> parseDeclareStmt();
      default -> parseExpr();
    };
  }

  // BlockStmt -> LBRACE Stmt* RBRACE
  private StmtNode parseBlockStmt() {
    expect(LBRACE);

    BlockStmtNode blockStmt = new BlockStmtNode();
    while (current().type() != RBRACE) {
      StmtNode stmt = parseStmt();
      blockStmt.addStatement(stmt);
    }

    expect(RBRACE);

    return blockStmt;
  }

  // DeclareStmt -> ( LET | CONST ) IDENTIFIER ( ASSIGN Expr )?
  private StmtNode parseDeclareStmt() {
    if (current().type() != LET && current().type() != CONST) {
      String message = String.format("Unexpected token of type %s", current().type());
      throw new CompilerException(message, current().location());
    }

    boolean isConst = advance().type() == CONST;
    Token varName = expect(IDENTIFIER);
    ExprNode expr = null;

    if (current().type() == ASSIGN) {
      expect(ASSIGN);
      expr = parseExpr();
    }

    if (isConst && expr == null) {
      String message = "Cannot declare a const variable without any value";
      throw new CompilerException(message, varName.location());
    }

    return new DeclareStmtNode(varName.content(), isConst, expr);
  }

  // Expr -> AssignExpr
  private ExprNode parseExpr() {
    return parseAssignExpr();
  }

  // AssignExpr -> AddExpr
  // AssignExpr -> VarExpr ASSIGN AssignExpr
  private ExprNode parseAssignExpr() {
    ExprNode left = parseAddExpr();

    if (current().type() == ASSIGN) {
      if (!(left instanceof VarExprNode assignee)) {
        String message = String.format("Unexpected token of type %s", current().type());
        throw new CompilerException(message, current().location());
      }

      advance();

      ExprNode expr = parseAssignExpr();

      left = new AssignExprNode(assignee, expr);
    }

    return left;
  }

  // AddExpr -> MulExpr ( ( PLUS | MINUS ) MulExpr )*
  private ExprNode parseAddExpr() {
    ExprNode left = parseMulExpr();

    while (isAddOperator(current())) {
      Token operator = advance();
      ExprNode right = parseMulExpr();

      left = new BinaryExprNode(left, right, operator.type());
    }

    return left;
  }

  private static boolean isAddOperator(Token token) {
    return token.type() == PLUS || token.type() == MINUS;
  }

  // MulExpr -> UnaryExpr ( ( MULTIPLY | DIVIDE ) UnaryExpr )*
  private ExprNode parseMulExpr() {
    ExprNode left = parseUnaryExpr();

    while (isMulOperator(current())) {
      Token operator = advance();
      ExprNode right = parseUnaryExpr();

      left = new BinaryExprNode(left, right, operator.type());
    }

    return left;
  }

  private static boolean isMulOperator(Token token) {
    return token.type() == MULTIPLY || token.type() == DIVIDE;
  }

  // UnaryExpr -> ( ( PLUS | MINUS | NOT ) UnaryExpr ) | PrimaryExpr
  private ExprNode parseUnaryExpr() {
    if (isUnaryOperator(current())) {
      return new UnaryExprNode(advance().type(), parseUnaryExpr());
    }

    return parsePrimaryExpr();
  }

  private static boolean isUnaryOperator(Token token) {
    return token.type() == PLUS || token.type() == MINUS || token.type() == NOT;
  }

  // PrimaryExpr -> NUMBER | TRUE | FALSE | STRING | IDENTIFIER | ParenExpr
  private ExprNode parsePrimaryExpr() throws CompilerException {
    Token t = current();

    return switch (t.type()) {
      case NUMBER -> parseNumeric(advance());
      case STRING -> parseString(advance());
      case IDENTIFIER -> new VarExprNode(advance().content());
      case LPAREN -> parseParenExpr();
      default -> {
        String message = String.format("Unexpected token of type %s", t.type());
        throw new CompilerException(message, t.location());
      }
    };
  }

  private static NumericExprNode parseNumeric(Token token) {
    double value = Double.parseDouble(token.content());
    return new NumericExprNode(value);
  }

  private static StringExprNode parseString(Token token) {
    String value = token.content();
    value = value.substring(1, value.length() - 1);
    return new StringExprNode(value);
  }

  // ParenExpr -> LPAREN Expr RPAREN
  private ExprNode parseParenExpr() {
    expect(LPAREN);
    ExprNode expr = parseExpr();
    expect(RPAREN);

    return expr;
  }
}
