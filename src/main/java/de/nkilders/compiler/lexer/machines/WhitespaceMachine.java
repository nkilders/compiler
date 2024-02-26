package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

public class WhitespaceMachine extends LexerMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var whitespace = state("whitespace", true);
        var err = errorState();

        init.addTransition(whitespace, "\\s")
            .setFallbackTransitionState(err);
        
        whitespace.addTransition(whitespace, "\\s")
                  .setFallbackTransitionState(err);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.WHITESPACE;
    }
}
