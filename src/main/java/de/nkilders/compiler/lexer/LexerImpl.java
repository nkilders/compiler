package de.nkilders.compiler.lexer;

import static de.nkilders.compiler.TokenType.DIV;
import static de.nkilders.compiler.TokenType.EQUALS;
import static de.nkilders.compiler.TokenType.LBRACE;
import static de.nkilders.compiler.TokenType.LBRACKET;
import static de.nkilders.compiler.TokenType.LPAREN;
import static de.nkilders.compiler.TokenType.MINUS;
import static de.nkilders.compiler.TokenType.MUL;
import static de.nkilders.compiler.TokenType.PLUS;
import static de.nkilders.compiler.TokenType.RBRACE;
import static de.nkilders.compiler.TokenType.RBRACKET;
import static de.nkilders.compiler.TokenType.RPAREN;

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

            if(step == 0) {
                throwInvalidCharacter(input, pos);
            }

            LexerMachine bestMachine = getMachineWithMostSteps();
            String text = input.substring(pos, pos+step);

            LOGGER.info("Best machine: {}", bestMachine);
            LOGGER.info("Number of steps: {}", step);
            LOGGER.info("Text: \"{}\"", text);

            tokens.add(buildToken(bestMachine.getTokenType(), text));

            pos += step;
        }

        return tokens;
    }

    private void throwInvalidCharacter(String input, int pos) {
        String msg = String.format("Unable to process character \"%s\"", input.charAt(pos));
        LineCol lineCol = Util.calculateLineAndCol(input, pos);
        
        throw new CompilerException(msg, lineCol);
    }

    private void createMachines() {
        this.machines = List.of(
            new LineCommentMachine(),
            new WhitespaceMachine(),
            new StringMachine(),
            new IdentifierMachine(),
            new NumberMachine(),
            new KeywordMachine("+", PLUS),
            new KeywordMachine("-", MINUS),
            new KeywordMachine("*", MUL),
            new KeywordMachine("/", DIV),
            new KeywordMachine("=", EQUALS),
            new KeywordMachine("(", LPAREN),
            new KeywordMachine(")", RPAREN),
            new KeywordMachine("{", LBRACE),
            new KeywordMachine("}", RBRACE),
            new KeywordMachine("[", LBRACKET),
            new KeywordMachine("]", RBRACKET)
        );
    }

    private Token buildToken(TokenType type, String content) {
        if(type == TokenType.IDENTIFIER) {
            TokenType reservedKeywordType = ReservedKeyword.get(content);

            if(reservedKeywordType != null) {
                return new Token(reservedKeywordType, content);
            }
        }

        return new Token(type, content);
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
