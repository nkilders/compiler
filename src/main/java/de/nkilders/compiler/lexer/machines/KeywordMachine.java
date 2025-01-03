package de.nkilders.compiler.lexer.machines;

import static java.util.Objects.requireNonNull;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

/**
 * A lexer machine that recognizes keywords.
 * <p>
 * Keywords are reserved words that have a special meaning in the language. They
 * cannot be used as identifiers.
 * <p>
 * This machine recognizes a single keyword.
 */
public class KeywordMachine extends LexerMachine {
  private final String keyword;
  private final TokenType tokenType;

  /**
   * @param keyword   the keyword to recognize (must not be null)
   * @param tokenType the token type to return when the keyword is recognized
   */
  public KeywordMachine(String keyword, TokenType tokenType) {
    super(false);

    this.keyword = requireNonNull(keyword);
    this.tokenType = tokenType;

    super.init();
  }

  @Override
  protected void initStatesAndTransitions() {
    var init = initialState();
    var err = errorState();

    String[] chars = keyword.split("");
    State[] states = new State[chars.length];

    // Create a state for each character in the keyword
    for (int i = 0; i < states.length; i++) {
      String stateName = String.format("#%d", i);
      boolean isLastState = i == (states.length - 1);

      states[i] = state(stateName, isLastState);
    }

    // Link first state
    var firstState = states[0];
    init.addTransition(firstState, escapeRegEx(chars[0]))
        .setFallbackTransitionState(err);

    // Link middle states
    for (int i = 0; i < (states.length - 1); i++) {
      var currentState = states[i];
      var nextState = states[i + 1];
      var escapedChar = escapeRegEx(chars[i + 1]);

      currentState.addTransition(nextState, escapedChar)
          .setFallbackTransitionState(err);
    }

    // Link last state
    var lastState = states[states.length - 1];
    lastState.setFallbackTransitionState(err);
  }

  private static String escapeRegEx(String input) {
    return input.replace("(", "\\(")
        .replace(")", "\\)")
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("+", "\\+")
        .replace("*", "\\*");
  }

  @Override
  public TokenType getTokenType() {
    return tokenType;
  }
}
