package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

public class StringMachine extends LexerMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var start = state("start");
        var content = state("content");
        var escape = state("escape");
        var end = state("end", true);
        var err = errorState();

        init.addTransition(start, "\"")
            .setFallbackTransitionState(err);

        start.addTransition(escape, "\\\\")
             .addTransition(content, ".")
             .addTransition(end, "\"")
             .setFallbackTransitionState(err);

        content.addTransition(escape, "\\\\")
               .addTransition(end, "\"")
               .addTransition(content, ".")
               .setFallbackTransitionState(err);

        escape.addTransition(content, "[\\\\\"]")
              .setFallbackTransitionState(err);

        end.setFallbackTransitionState(err);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.STRING;
    }
    
}
