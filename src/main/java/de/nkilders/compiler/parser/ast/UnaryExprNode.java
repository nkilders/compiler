package de.nkilders.compiler.parser.ast;

import static de.nkilders.compiler.TokenType.MINUS;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.PLUS;
import static java.util.Objects.requireNonNull;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.BooleanValue;
import de.nkilders.compiler.interpreter.values.NumberValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

/**
 * Represents a unary expression node in the AST
 * <p>
 * A unary expression consists of an operator and an expression.
 * <p>
 * The result of the unary expression depends on the type of the expression and
 * the operator.
 * <p>
 * If the expression evaluates to {@link BooleanValue} and the operator is
 * {@link TokenType#NOT}, the boolean value will be negated. The result will be
 * a {@link BooleanValue}.
 * <p>
 * If the expression evaluates to {@link NumberValue} and the operator is
 * {@link TokenType#PLUS} or {@link TokenType#MINUS}, the number will be
 * multiplied by 1 or -1 respectively. The result will be a {@link NumberValue}.
 * <p>
 * If the expression or operator is of a different type, an
 * {@link UnsupportedOperationException} will be thrown.
 */
public class UnaryExprNode extends ExprNode {
  private TokenType operator;
  private ExprNode expression;

  /**
   * @param operator   the operator
   * @param expression the expression (must not be null)
   */
  public UnaryExprNode(TokenType operator, ExprNode expression) {
    this.operator = operator;
    this.expression = requireNonNull(expression);
  }

  @Override
  public RuntimeValue<?> eval(Environment env) {
    RuntimeValue<?> expVal = expression.eval(env);

    if (expVal instanceof BooleanValue bool && operator == NOT) {
      boolean result = !bool.getValue();
      return new BooleanValue(result);
    }

    if (expVal instanceof NumberValue num && (operator == PLUS || operator == MINUS)) {
      double result = num.getValue();
      if (operator == MINUS) {
        result *= -1;
      }
      return new NumberValue(result);
    }

    throw unsupportedOperation(expVal);
  }

  private RuntimeException unsupportedOperation(RuntimeValue<?> val) {
    String message = String.format("Operator %s is not defined for %s", operator, val.getClass()
        .getSimpleName());
    return new UnsupportedOperationException(message);
  }

  public TokenType getOperator() {
    return operator;
  }

  public ExprNode getExpression() {
    return expression;
  }

  @Override
  public String toString() {
    return "UnaryExprNode [operator=" + operator + ", expression=" + expression + "]";
  }
}
