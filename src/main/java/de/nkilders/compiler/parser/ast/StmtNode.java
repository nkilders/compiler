package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;

public abstract class StmtNode extends AstNode {
  public abstract void exec(Environment env);
}
