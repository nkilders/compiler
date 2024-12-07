package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class AssignExprNode extends ExprNode {
  private final VarExprNode assignee;
  private final ExprNode expr;

  public AssignExprNode(VarExprNode assignee, ExprNode expr) {
    this.assignee = assignee;
    this.expr = expr;
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
