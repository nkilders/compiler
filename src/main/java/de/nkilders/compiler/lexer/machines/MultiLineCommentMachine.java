package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

public class MultiLineCommentMachine extends LexerMachine {
    
    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var startSlash = state("start slash");
        var startStar = state("start star");
        var escape = state("escape");
        var content = state("content");
        var endStar = state("end star");
        var endSlash = state("end slash", true);
        var err = errorState();

        init.addTransition(startSlash, "/")
            .setFallbackTransitionState(err);

        startSlash.addTransition(startStar, "\\*")
                  .setFallbackTransitionState(err);

        startStar.addTransition(escape, "\\\\")
                 .addTransition(endStar, "\\*")
                 .setFallbackTransitionState(content);

        escape.setFallbackTransitionState(content);

        content.addTransition(escape, "\\\\")
               .addTransition(endStar, "\\*")
               .setFallbackTransitionState(content);

        endStar.addTransition(endSlash, "/")
               .setFallbackTransitionState(err);

        endSlash.setFallbackTransitionState(err);
    }
    
    @Override
    public TokenType getTokenType() {
        return TokenType.MULTI_LINE_COMMENT;
    }
}
