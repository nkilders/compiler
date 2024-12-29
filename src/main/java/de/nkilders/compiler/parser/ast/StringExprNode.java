package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.StringValue;

/**
 * Represents a string expression node in the AST.
 * <p>
 * A string expression consists of a single string value which evaluates to a
 * {@link StringValue}.
 */
public class StringExprNode extends ExprNode {
  private String value;

  public StringExprNode(String value) {
    this.value = value;
  }

  @Override
  public StringValue eval(Environment env) {
    return new StringValue(value);
  }

  @Override
  public String toString() {
    return "StringExprNode [value=" + value + "]";
  }
}
