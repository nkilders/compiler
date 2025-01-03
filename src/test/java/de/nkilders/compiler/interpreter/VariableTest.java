package de.nkilders.compiler.interpreter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.nkilders.compiler.interpreter.values.NullValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

class VariableTest {

  @Test
  void setValue() {
    Variable v = new Variable("myVar", false);

    RuntimeValue<?> newValue = new NullValue();
    assertDoesNotThrow(() -> v.setValue(newValue));
    assertEquals(newValue, v.getValue());
  }

  @Test
  void setValue_errorIfConst() {
    Variable v = new Variable("myVar", true);

    RuntimeValue<?> newValue = new NullValue();
    assertThrows(VarException.class, () -> v.setValue(newValue));
    assertNull(v.getValue());
  }
}
