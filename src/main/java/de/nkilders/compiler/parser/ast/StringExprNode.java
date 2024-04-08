package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.values.StringValue;

public class StringExprNode extends ExprNode {
    private String value;

    public StringExprNode(String value) {
        this.value = value;
    }

    @Override
    public StringValue eval() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return "StringExprNode [value=" + value + "]";
    }
}
