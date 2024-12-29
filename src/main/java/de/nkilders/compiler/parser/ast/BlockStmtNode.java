package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.interpreter.Environment;

/**
 * Represents a block statement node in the AST.
 * <p>
 * A block statement consists of multiple statements that are executed
 * sequentially.
 * <p>
 * A block statement creates a new environment for its statements. This
 * environment is a child of the environment in which the block statement is
 * executed.
 */
public class BlockStmtNode extends StmtNode {
  private final List<StmtNode> statements;
  private Environment environment;

  public BlockStmtNode() {
    this.statements = new ArrayList<>();
  }

  public void addStatement(StmtNode statement) {
    this.statements.add(statement);
  }

  public List<StmtNode> getStatements() {
    return statements;
  }

  @Override
  public void exec(Environment parentEnv) {
    if (environment == null) {
      environment = new Environment(parentEnv);
    }

    statements.forEach(stmt -> stmt.exec(environment));
  }

  @Override
  public String toString() {
    return "BlockStmtNode [statements=" + statements + "]";
  }
}
