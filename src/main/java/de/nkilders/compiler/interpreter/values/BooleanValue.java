package de.nkilders.compiler.interpreter.values;

public class BooleanValue implements RuntimeValue<Boolean> {
    private boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BooleanValue [value=" + value + "]";
    }
}
