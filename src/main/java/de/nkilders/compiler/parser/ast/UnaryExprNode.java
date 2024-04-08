package de.nkilders.compiler.parser.ast;

import static de.nkilders.compiler.TokenType.NOT;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.interpreter.values.BooleanValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class UnaryExprNode extends ExprNode {
    private TokenType operator;
    private ExprNode expression;

    public UnaryExprNode(TokenType operator, ExprNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public RuntimeValue<?> eval() {
        RuntimeValue<?> expVal = expression.eval();
        
        if(expVal instanceof BooleanValue bool && operator == NOT) {
            boolean result = !bool.getValue();
            return new BooleanValue(result);
        }

        throw unsupportedOperation(expVal);
    }

    private RuntimeException unsupportedOperation(RuntimeValue<?> val) {
        String message = String.format("Operator %s is not defined for %s",
            operator,
            val.getClass().getSimpleName()
        );
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
