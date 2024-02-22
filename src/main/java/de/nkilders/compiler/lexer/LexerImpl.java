package de.nkilders.compiler.lexer;

import static de.nkilders.compiler.TokenType.DIV;
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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nkilders.compiler.Token;
import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.machine.LineCommentMachine;
import de.nkilders.compiler.machine.StateMachine;
import de.nkilders.compiler.machine.WhitespaceMachine;

public class LexerImpl implements Lexer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LexerImpl.class);

    private List<StateMachine> machines;

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
            Token singleCharToken = singleCharToken(chars[pos]);
            if(singleCharToken != null) {
                tokens.add(singleCharToken);
                pos++;
                continue;
            }

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

            StateMachine bestMachine = getMachineWithMostSteps();
            String text = input.substring(pos, pos+step);

            LOGGER.info("Best machine: {}", bestMachine);
            LOGGER.info("Number of steps: {}", step);
            LOGGER.info("Text: \"{}\"", text);

            tokens.add(new Token(TokenType.TEST, text));

            pos += step;
        }

        return tokens;
    }

    private Token singleCharToken(char c) {
        TokenType type = switch(c) {
            case '+' -> PLUS;
            case '-' -> MINUS;
            case '*' -> MUL;
            case '/' -> DIV;
            case '(' -> LPAREN;
            case ')' -> RPAREN;
            case '{' -> LBRACE;
            case '}' -> RBRACE;
            case '[' -> LBRACKET;
            case ']' -> RBRACKET;
            default -> null;
        };

        if(type == null) return null;

        return new Token(type, c + "");
    }

    private void createMachines() {
        this.machines = List.of(
            new LineCommentMachine(),
            new WhitespaceMachine()
        );
    }

    private boolean anyMachineActive() {
        return this.machines.stream().anyMatch(m -> !m.isInErrorState());
    }

    private StateMachine getMachineWithMostSteps() {
        StateMachine bestMachine = null;

        for(StateMachine machine : machines) {
            if(bestMachine == null) {
                bestMachine = machine;
                continue;
            }

            if(machine.getStepsBeforeError() > bestMachine.getStepsBeforeError()) {
                bestMachine = machine;
            }
        }

        return bestMachine;
    }

    private void resetMachines() {
        machines.forEach(StateMachine::reset);
    }
}
