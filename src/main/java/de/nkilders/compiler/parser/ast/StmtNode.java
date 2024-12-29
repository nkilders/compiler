package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;

/**
 * Represents a statement node in the AST.
 * <p>
 * A statement is a single line of code that can be executed. This class is the
 * base class for all statement nodes.
 */
public abstract class StmtNode extends AstNode {
  /**
   * Executes the statement node.
   * 
   * @param env The environment in which the statement is executed.
   */
  public abstract void exec(Environment env);
}
