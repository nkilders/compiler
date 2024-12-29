package de.nkilders.compiler.parser.ast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

/**
 * Represents an expression node in the AST.
 * <p>
 * An expression is a piece of code that can be evaluated to a value. This class
 * is the base class for all expression nodes.
 * <p>
 * This class extends {@link StmtNode} because expressions can also be used as
 * statements. This is useful for expressions that don't have any side effects,
 * like a simple arithmetic operation. In this case, the expression is evaluated
 * and the result is logged to the console.
 */
public abstract class ExprNode extends StmtNode {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExprNode.class);

  /**
   * Evaluates the expression node.
   * 
   * @param env The environment in which the expression is evaluated.
   * @return The runtime value of the expression.
   */
  @SuppressWarnings("rawtypes")
  public abstract RuntimeValue eval(Environment env);

  @Override
  public void exec(Environment env) {
    RuntimeValue<?> value = eval(env);
    LOGGER.info("Orphaned expression evaluated to {}", value);
  }
}
