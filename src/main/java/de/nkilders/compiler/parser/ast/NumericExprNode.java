package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.values.NumberValue;

public class NumericExprNode extends ExprNode {
    private double value;

    public NumericExprNode(double value) {
        this.value = value;
    }

    @Override
    public NumberValue eval() {
        return new NumberValue(value);
    }

    @Override
    public String toString() {
        return "NumericExprNode [value=" + value + "]";
    }
}
