package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.CLI;

/**
 * Base class for all nodes in the AST
 */
public abstract class AstNode {
  /**
   * Used for node identification when printing the AST in {@link CLI}
   */
  @SuppressWarnings("unused")
  private final String nodeType = getClass().getSimpleName();
}
