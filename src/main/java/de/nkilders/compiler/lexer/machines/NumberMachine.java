package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

public class NumberMachine extends LexerMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var sign = state("sign");
        var preDot = state("pre dot", true);
        var dot = state("dot");
        var postDot = state("post dot", true);
        var err = errorState();

        init.addTransition(sign, "[\\+-]")
            .addTransition(preDot, "\\d")
            .addTransition(dot, "\\.")
            .setFallbackTransitionState(err);

        sign.addTransition(preDot, "\\d")
            .addTransition(dot, "\\.")
            .setFallbackTransitionState(err);

        preDot.addTransition(preDot, "\\d")
              .addTransition(dot, "\\.")
              .setFallbackTransitionState(err);

        dot.addTransition(postDot, "\\d")
           .setFallbackTransitionState(err);

        postDot.addTransition(postDot, "\\d")
               .setFallbackTransitionState(err);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NUMBER;
    }
    
}
