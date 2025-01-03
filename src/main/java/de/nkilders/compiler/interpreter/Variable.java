package de.nkilders.compiler.interpreter;

import static java.util.Objects.requireNonNull;

import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class Variable {
  private final String name;
  private final boolean isConst;
  private RuntimeValue<?> value;

  /**
   * Creates a new variable with the given name, const flag and value.
   * 
   * @param name    the name of the variable (must not be null)
   * @param isConst whether the variable is const
   * @param value   the value of the variable (can be null)
   */
  public Variable(String name, boolean isConst, RuntimeValue<?> value) {
    this.name = requireNonNull(name);
    this.isConst = isConst;
    this.value = value;
  }

  /**
   * Creates a new variable with the given name and const flag. The value of the
   * variable is null.
   * 
   * @param name    the name of the variable (must not be null)
   * @param isConst whether the variable is const
   */
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
