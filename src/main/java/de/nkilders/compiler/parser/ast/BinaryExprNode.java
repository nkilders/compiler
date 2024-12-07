package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.NumberValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;
import de.nkilders.compiler.interpreter.values.StringValue;

public class BinaryExprNode extends ExprNode {
  private ExprNode left;
  private ExprNode right;
  private TokenType operator;

  public BinaryExprNode(ExprNode left, ExprNode right, TokenType operator) {
    this.left = left;
    this.right = right;
    this.operator = operator;
  }

  @Override
  public RuntimeValue<?> eval(Environment env) {
    RuntimeValue<?> l = left.eval(env);
    RuntimeValue<?> r = right.eval(env);

    if (l instanceof NumberValue ll && r instanceof NumberValue rr) {
      return evalNumNum(ll, rr);
    }

    if (l instanceof StringValue || r instanceof StringValue) {
      return evalStr(l, r);
    }

    throw unsupportedOperation(l, r);
  }

  private NumberValue evalNumNum(NumberValue left, NumberValue right) {
    double l = left.getValue();
    double r = right.getValue();

    double result = switch (operator) {
      case PLUS -> l + r;
      case MINUS -> l - r;
      case MULTIPLY -> l * r;
      case DIVIDE -> l / r;
      default -> throw unsupportedOperation(left, right);
    };

    return new NumberValue(result);
  }

  private StringValue evalStr(RuntimeValue<?> left, RuntimeValue<?> right) {
    String l = left.getValue()
        .toString();
    String r = right.getValue()
        .toString();

    String result = switch (operator) {
      case PLUS -> l.concat(r);
      default -> throw unsupportedOperation(left, right);
    };

    return new StringValue(result);
  }

  private RuntimeException unsupportedOperation(RuntimeValue<?> l, RuntimeValue<?> r) {
    String message = String.format("Operator %s is not defined for %s and %s", operator, l.getClass()
        .getSimpleName(),
        r.getClass()
            .getSimpleName());
    return new UnsupportedOperationException(message);
  }

  public ExprNode getLeft() {
    return left;
  }

  public ExprNode getRight() {
    return right;
  }

  public TokenType getOperator() {
    return operator;
  }

  @Override
  public String toString() {
    return "BinaryExprNode [left=" + left + ", right=" + right + ", operator=" + operator + "]";
  }
}
