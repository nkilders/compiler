package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.NumberValue;

/**
 * Represents a numeric expression node in the AST.
 * <p>
 * A numeric expression consists of a single numeric value which evaluates to a
 * {@link NumberValue}.
 */
public class NumericExprNode extends ExprNode {
  private double value;

  public NumericExprNode(double value) {
    this.value = value;
  }

  @Override
  public NumberValue eval(Environment env) {
    return new NumberValue(value);
  }

  @Override
  public String toString() {
    return "NumericExprNode [value=" + value + "]";
  }
}
