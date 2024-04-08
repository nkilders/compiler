package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.values.BooleanValue;

public class BooleanExprNode extends ExprNode {
    private boolean value;

    public BooleanExprNode(boolean value) {
        this.value = value;
    }

    @Override
    public BooleanValue eval() {
        return new BooleanValue(value);
    }

    @Override
    public String toString() {
        return "BooleanExprNode [value=" + value + "]";
    }
}
