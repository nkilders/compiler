package de.nkilders.compiler.parser.ast;

public class UnaryExprNode extends ExprNode {
    private String operator;
    private ExprNode expression;

    public UnaryExprNode(String operator, ExprNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public double eval() {
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
    public String getOperator() {
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
