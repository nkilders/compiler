package de.nkilders.compiler.parser.ast;

public class NumericExprNode extends ExprNode {
    private double value;

    public NumericExprNode(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return value;
    }

    @Override
    public String toString() {
        return "NumericExprNode [value=" + value + "]";
    }
}
