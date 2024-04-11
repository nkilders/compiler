package de.nkilders.compiler.lexer;

import static de.nkilders.compiler.TokenType.AND;
import static de.nkilders.compiler.TokenType.ASSIGN;
import static de.nkilders.compiler.TokenType.COMMA;
import static de.nkilders.compiler.TokenType.DIVIDE;
import static de.nkilders.compiler.TokenType.EOF;
import static de.nkilders.compiler.TokenType.EQUALS;
import static de.nkilders.compiler.TokenType.GT;
import static de.nkilders.compiler.TokenType.GTE;
import static de.nkilders.compiler.TokenType.LBRACE;
import static de.nkilders.compiler.TokenType.LBRACKET;
import static de.nkilders.compiler.TokenType.LPAREN;
import static de.nkilders.compiler.TokenType.LT;
import static de.nkilders.compiler.TokenType.LTE;
import static de.nkilders.compiler.TokenType.MINUS;
import static de.nkilders.compiler.TokenType.MODULO;
import static de.nkilders.compiler.TokenType.MULTIPLY;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.OR;
import static de.nkilders.compiler.TokenType.PLUS;
import static de.nkilders.compiler.TokenType.RBRACE;
import static de.nkilders.compiler.TokenType.RBRACKET;
import static de.nkilders.compiler.TokenType.RPAREN;
import static de.nkilders.compiler.TokenType.SEMICOLON;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nkilders.compiler.CompilerException;
import de.nkilders.compiler.ReservedKeyword;
import de.nkilders.compiler.Token;
import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.machines.IdentifierMachine;
import de.nkilders.compiler.lexer.machines.KeywordMachine;
import de.nkilders.compiler.lexer.machines.LineCommentMachine;
import de.nkilders.compiler.lexer.machines.MultiLineCommentMachine;
import de.nkilders.compiler.lexer.machines.NumberMachine;
import de.nkilders.compiler.lexer.machines.StringMachine;
import de.nkilders.compiler.lexer.machines.WhitespaceMachine;
import de.nkilders.compiler.util.StateMachine;
import de.nkilders.compiler.util.Util;
import de.nkilders.compiler.util.Util.LineCol;

public class LexerImpl implements Lexer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LexerImpl.class);

    private List<LexerMachine> machines;

    public LexerImpl() {
        this.machines = new ArrayList<>();

        this.createMachines();
    }

    @Override
    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();

        char[] chars = input.toCharArray();

        int pos = 0;
        
        while(pos < input.length()) {
            resetMachines();

            int step = 0;

            do {
                char currentChar = chars[pos + step];
                machines.forEach(m -> m.step(currentChar));

                if(!anyMachineActive()) {
                    break;
                }

                step++;
            } while((pos+step) < input.length());

            LineCol lineCol = Util.calculateLineAndCol(input, pos);

            if(step == 0) {
                String msg = String.format("Unable to process character \"%s\"", input.charAt(pos));
                throw new CompilerException(msg, lineCol);
            }

            LexerMachine bestMachine = getMachineWithMostSteps();
            String text = input.substring(pos, pos+step);

            LOGGER.debug("Best machine: {}", bestMachine);
            LOGGER.debug("Number of steps: {}", step);
            LOGGER.debug("Text: \"{}\"", text);

            tokens.add(buildToken(bestMachine.getTokenType(), text, lineCol));

            pos += step;
        }

        LineCol lineCol = Util.calculateLineAndCol(input, pos);
        tokens.add(buildToken(EOF, "", lineCol));

        return tokens;
    }

    private void createMachines() {
        this.machines = List.of(
            // Static
            new KeywordMachine("(", LPAREN),
            new KeywordMachine(")", RPAREN),
            new KeywordMachine("{", LBRACE),
            new KeywordMachine("}", RBRACE),
            new KeywordMachine("[", LBRACKET),
            new KeywordMachine("]", RBRACKET),
            new KeywordMachine("+", PLUS),
            new KeywordMachine("-", MINUS),
            new KeywordMachine("*", MULTIPLY),
            new KeywordMachine("/", DIVIDE),
            new KeywordMachine("%", MODULO),
            new KeywordMachine("==", EQUALS),
            new KeywordMachine("=", ASSIGN),
            new KeywordMachine(">", GT),
            new KeywordMachine(">=", GTE),
            new KeywordMachine("<", LT),
            new KeywordMachine("<=", LTE),
            new KeywordMachine(";", SEMICOLON),
            new KeywordMachine(",", COMMA),
            new KeywordMachine("&&", AND),
            new KeywordMachine("||", OR),
            new KeywordMachine("!", NOT),
        
            // Dynamic
            new LineCommentMachine(),
            new MultiLineCommentMachine(),
            new WhitespaceMachine(),
            new StringMachine(),
            new IdentifierMachine(),
            new NumberMachine()
        );
    }

    private Token buildToken(TokenType type, String content, LineCol lineCol) {
        if(type == TokenType.IDENTIFIER) {
            TokenType reservedKeywordType = ReservedKeyword.get(content);

            if(reservedKeywordType != null) {
                type = reservedKeywordType;
            }
        }

        return new Token(type, content, lineCol);
    }

    private boolean anyMachineActive() {
        return this.machines.stream().anyMatch(m -> !m.isInErrorState());
    }

    private LexerMachine getMachineWithMostSteps() {
        return machines.stream()
                        // When the last character of a file has been read, not all machines have to
                        // be in error state. For that case  we need to filter based on both conditions.
                       .filter(m -> m.wasInFinalStateBeforeError() || m.isInFinalState())
                       .sorted(Comparator.comparing(LexerMachine::getStepsBeforeError))
                       .toList()
                       .getLast();
    }

    private void resetMachines() {
        machines.forEach(StateMachine::reset);
    }
}
