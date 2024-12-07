package de.nkilders.compiler.interpreter.values;

public class StringValue implements RuntimeValue<String> {
  private String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "StringValue [value=" + value + "]";
  }
}
