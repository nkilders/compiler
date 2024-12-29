package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

/**
 * A lexer machine that recognizes strings.
 * <p>
 * A string is a sequence of characters enclosed in double quotes ({@code "}).
 * It can contain any characters, including escape sequences. Escape sequences
 * are used to represent special characters, such as backslashes and double
 * quotes, and start with a backslash ({@code \}). The escape sequences
 * recognized by this machine are:
 * <ul>
 * <li>{@code \\} - backslash</li>
 * <li>{@code \"} - double quote</li>
 * </ul>
 */
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
