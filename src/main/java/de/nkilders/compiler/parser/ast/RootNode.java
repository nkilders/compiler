package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.interpreter.Environment;

/**
 * Represents the root node of the AST.
 * <p>
 * The root node is the top-level node of the AST and contains all the
 * statements of the program. It is the entry point for the interpreter.
 */
public class RootNode extends AstNode {
  private final List<StmtNode> statements;

  public RootNode() {
    this.statements = new ArrayList<>();
  }

  public void addStatement(StmtNode stmt) {
    this.statements.add(stmt);
  }

  public void run(Environment environment) {
    statements.stream()
        .forEach(stmt -> stmt.exec(environment));
  }

  public List<StmtNode> getStatements() {
    return statements;
  }

  @Override
  public String toString() {
    return "RootNode [statements=" + statements + "]";
  }
}
