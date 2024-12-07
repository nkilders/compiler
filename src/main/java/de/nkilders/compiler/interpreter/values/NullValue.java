package de.nkilders.compiler.interpreter.values;

public class NullValue implements RuntimeValue<Object> {

  @Override
  public Object getValue() {
    return null;
  }

  @Override
  public String toString() {
    return "NullValue []";
  }
}
