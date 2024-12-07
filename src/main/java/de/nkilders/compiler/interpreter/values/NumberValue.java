package de.nkilders.compiler.interpreter.values;

public class NumberValue implements RuntimeValue<Double> {
  private double value;

  public NumberValue(double value) {
    this.value = value;
  }

  @Override
  public Double getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "NumberValue [value=" + value + "]";
  }
}
