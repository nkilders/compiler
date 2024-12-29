package de.nkilders.compiler.lexer;

import java.util.List;

import de.nkilders.compiler.Token;

/**
 * Interface for a lexer that generates a list of tokens from an input string.
 * The tokens can then be used by a parser to generate an abstract syntax tree
 * (AST).
 */
public interface Lexer {
  /**
   * Tokenizes the given input string and returns a list of tokens.
   * 
   * @param input The input string to tokenize.
   * @return A list of tokens.
   */
  List<Token> tokenize(String input);
}
