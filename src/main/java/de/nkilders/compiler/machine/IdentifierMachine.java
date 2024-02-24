package de.nkilders.compiler.machine;

import de.nkilders.compiler.TokenType;

public class IdentifierMachine extends LexerMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var first = state("first", true);
        var more = state("more", true);
        var err = errorState();

        init.addTransition(first, "[a-zA-Z_]")
            .setFallbackTransitionState(err);

        first.addTransition(more, "\\w")
             .setFallbackTransitionState(err);

        more.addTransition(more, "\\w")
            .setFallbackTransitionState(err);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.IDENTIFIER;
    }
}
