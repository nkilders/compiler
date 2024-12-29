package de.nkilders.compiler.interpreter;

import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class Variable {
  private final String name;
  private final boolean isConst;
  private RuntimeValue<?> value;

  public Variable(String name, boolean isConst, RuntimeValue<?> value) {
    this.name = name;
    this.isConst = isConst;
    this.value = value;
  }

  public Variable(String name, boolean isConst) {
    this(name, isConst, null);
  }

  public String getName() {
    return name;
  }

  public boolean isConst() {
    return isConst;
  }

  @SuppressWarnings("rawtypes")
  public RuntimeValue getValue() {
    return value;
  }

  /**
   * @throws VarException if the variable is const
   */
  public void setValue(RuntimeValue<?> value) throws VarException {
    if (isConst) {
      String message = String.format("Cannot set value of variable %s since it is const", name);
      throw new VarException(message);
    }

    this.value = value;
  }

  @Override
  public String toString() {
    return "Variable [name=" + name + ", isConst=" + isConst + ", value=" + value + "]";
  }
}
