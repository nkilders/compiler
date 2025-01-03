package de.nkilders.compiler.lexer.machines;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.lexer.LexerMachine;

/**
 * A lexer machine that recognizes line comments.
 * <p>
 * A line comment starts with two slashes ({@code //}) and continues until the
 * end of the line.
 */
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
