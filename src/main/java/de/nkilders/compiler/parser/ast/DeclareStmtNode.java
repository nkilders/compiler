package de.nkilders.compiler.parser.ast;

import static java.util.Objects.requireNonNull;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

/**
 * Represents a declare statement node in the AST.
 * <p>
 * A declare statement consists of a variable name, a boolean indicating whether
 * the variable is constant, and an optional expression.
 * <p>
 * If an expression is present, it is evaluated and the result is assigned to
 * the variable. If no expression is present, the variable is declared without a
 * value.
 */
public class DeclareStmtNode extends StmtNode {
  private final String varName;
  private final boolean isConst;
  private final ExprNode expr;

  /**
   * @param varName the name of the variable (must not be null)
   * @param isConst whether the variable is constant
   * @param expr    the expression to evaluate and assign (can be null)
   */
  public DeclareStmtNode(String varName, boolean isConst, ExprNode expr) {
    this.varName = requireNonNull(varName);
    this.isConst = isConst;
    this.expr = expr;
  }

  /**
   * @param varName the name of the variable (must not be null)
   * @param isConst whether the variable is constant
   */
  public DeclareStmtNode(String varName, boolean isConst) {
    this(varName, isConst, null);
  }

  @Override
  public void exec(Environment env) {
    RuntimeValue<?> value = null;

    if (expr != null) {
      value = expr.eval(env);
    }

    env.declareVariable(varName, isConst, value);
  }
}
