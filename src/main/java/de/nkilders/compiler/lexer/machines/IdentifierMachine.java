package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

/**
 * A lexer machine that recognizes identifiers.
 * <p>
 * An identifier is a sequence of letters, digits, and underscores that does not
 * start with a digit. Identifiers are used to name variables, functions, and
 * other entities in the source code.
 */
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
