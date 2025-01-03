package de.nkilders.compiler.parser.ast;

import static java.util.Objects.requireNonNull;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.NumberValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;
import de.nkilders.compiler.interpreter.values.StringValue;

/**
 * Represents a binary expression node in the AST.
 * <p>
 * A binary expression consists of a left and right expression and an operator.
 * <p>
 * The result of the binary expression depends on the types of the left and
 * right expressions and the operator.
 * <p>
 * If both expressions evaluate to {@link NumberValue}, a numeric operation will
 * be performed. The result will be a {@link NumberValue}. Allowed operators are
 * {@link TokenType#PLUS}, {@link TokenType#MINUS}, {@link TokenType#MULTIPLY}
 * and {@link TokenType#DIVIDE}.
 * <p>
 * If at least one expression evaluates to {@link StringValue} and the operator
 * is {@link TokenType#PLUS}, both expressions will be converted to strings and
 * concatenated. The result will be a {@link StringValue}.
 * <p>
 * If the expressions or operator are of different types, an
 * {@link UnsupportedOperationException} will be thrown.
 */
public class BinaryExprNode extends ExprNode {
  private ExprNode left;
  private ExprNode right;
  private TokenType operator;

  /**
   * @param left     the left expression (must not be null)
   * @param right    the right expression (must not be null)
   * @param operator the operator (must not be null)
   */
  public BinaryExprNode(ExprNode left, ExprNode right, TokenType operator) {
    this.left = requireNonNull(left);
    this.right = requireNonNull(right);
    this.operator = requireNonNull(operator);
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
