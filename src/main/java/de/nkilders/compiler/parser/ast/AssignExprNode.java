package de.nkilders.compiler.parser.ast;

import static java.util.Objects.requireNonNull;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

/**
 * Represents an assignment expression node in the AST.
 * <p>
 * An assignment expression consists of a {@link VarExprNode} and an expression.
 * <p>
 * When evaluated, the expression is evaluated and the result is assigned to the
 * variable. The value of the expression is also returned as the assignment
 * expression's value.
 */
public class AssignExprNode extends ExprNode {
  private final VarExprNode assignee;
  private final ExprNode expr;

  /**
   * @param assignee the variable to assign to (must not be null)
   * @param expr     the expression to evaluate and assign (must not be null)
   */
  public AssignExprNode(VarExprNode assignee, ExprNode expr) {
    this.assignee = requireNonNull(assignee);
    this.expr = requireNonNull(expr);
  }

  @Override
  public RuntimeValue<?> eval(Environment env) {
    RuntimeValue<?> value = expr.eval(env);

    env.assignVariable(assignee.getVarName(), value);

    return value;
  }

  @Override
  public String toString() {
    return "AssignExprNode [assignee=" + assignee + ", expr=" + expr + "]";
  }
}
