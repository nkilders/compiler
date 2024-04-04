package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.Token;

public class BinaryExprNode extends ExprNode {
    private ExprNode left;
    private ExprNode right;
    private Token operator;

    public BinaryExprNode(ExprNode left, ExprNode right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double eval() {
        double l = left.eval();
        double r = right.eval();

        return switch(operator.type()) {
            case PLUS -> l + r;
            case MINUS -> l - r;
            case MULTIPLY -> l * r;
            case DIVIDE -> l / r;
            default -> throw new UnsupportedOperationException(operator.type().name());
        };
    }

    public ExprNode getLeft() {
        return left;
    }

    public ExprNode getRight() {
        return right;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "BinaryExprNode [left=" + left + ", right=" + right + ", operator=" + operator + "]";
    }   
}
