package de.nkilders.compiler.parser;

import java.util.List;

import de.nkilders.compiler.Token;
import de.nkilders.compiler.parser.ast.RootNode;

/**
 * Interface for a parser that generates an abstract syntax tree (AST) from a
 * list of tokens. The AST can then be used by an interpreter to execute the
 * program.
 */
public interface Parser {
  /**
   * Parses the given list of tokens and returns the root node of the generated
   * AST.
   * 
   * @param tokens The list of tokens to parse.
   * @return The root node of the generated AST.
   */
  RootNode parse(List<Token> tokens);
}
