package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

/**
 * A lexer machine that recognizes whitespace.
 * <p>
 * Whitespace is a sequence of one or more space, tab, or newline characters.
 * Whitespace is used to separate tokens and is ignored by the compiler.
 */
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
