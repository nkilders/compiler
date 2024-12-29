package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

/**
 * Represents a variable expression node in the AST.
 * <p>
 * A variable expression consists of a single variable name.
 * <p>
 * When evaluated, the variable's value is read from the current environment.
 */
public class VarExprNode extends ExprNode {
  private String varName;

  public VarExprNode(String varName) {
    this.varName = varName;
  }

  @Override
  public RuntimeValue<?> eval(Environment env) {
    return env.readVariable(varName);
  }

  public String getVarName() {
    return varName;
  }
}
