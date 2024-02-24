package de.nkilders.compiler.machine;

import de.nkilders.compiler.TokenType;

public class LineCommentMachine extends LexerMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var firstSlash = state("first slash");
        var secondSlash = state("second slash", true);
        var content = state("content", true);
        var err = errorState();

        init.addTransition(firstSlash, "/")
            .setFallbackTransitionState(err);

        firstSlash.addTransition(secondSlash, "/")
                  .setFallbackTransitionState(err);

        secondSlash.addTransition(content, ".")
                   .setFallbackTransitionState(err);

        content.addTransition(content, ".")
               .setFallbackTransitionState(err);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.LINE_COMMENT;
    }
}
