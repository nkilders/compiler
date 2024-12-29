package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

/**
 * A lexer machine that recognizes numbers.
 * <p>
 * A number is a sequence of digits that can contain a single dot to represent a
 * floating-point number. It can also start with a dot to represent a number
 * less than one.
 */
public class NumberMachine extends LexerMachine {

  @Override
  protected void initStatesAndTransitions() {
    var init = initialState();
    var preDot = state("pre dot", true);
    var dot = state("dot");
    var postDot = state("post dot", true);
    var err = errorState();

    init.addTransition(preDot, "\\d")
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
