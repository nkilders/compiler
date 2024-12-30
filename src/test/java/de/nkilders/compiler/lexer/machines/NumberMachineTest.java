package de.nkilders.compiler.lexer.machines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.nkilders.compiler.TokenType;

class NumberMachineTest {
  private NumberMachine machine;

  @BeforeEach
  void beforeEach() {
    machine = new NumberMachine();
  }

  @ParameterizedTest
  @CsvSource({ "123,3", // integer
      "123.456,7", // floating point
      ".123,4" // less than one
  })
  void happyPath(String input, int stepsBeforeError) {
    machine.processText(input);

    assertTrue(machine.isInFinalState(), "Machine is not in final state");
    assertFalse(machine.isInErrorState(), "Machine is in error state");
    assertEquals(stepsBeforeError, machine.getStepsBeforeError(), "Wrong number of steps before error");
  }

  @ParameterizedTest
  @CsvSource({ "abc,1", // invalid characters
      "123.456.789,8", // multiple dots
  })
  void errorAndNotFinalState(String input, int stepsBeforeError) {
    machine.processText(input);

    assertFalse(machine.isInFinalState(), "Machine is in final state");
    assertTrue(machine.isInErrorState(), "Machine is not in error state");
    assertEquals(stepsBeforeError, machine.getStepsBeforeError(), "Wrong number of steps before error");
  }

  @ParameterizedTest
  @CsvSource({ "'',0", // empty input
      ".,1", // only a dot
      "123.,4" // dot at the end
  })
  void neitherFinalNorErrorState(String input, int stepsBeforeError) {
    machine.processText(input);

    assertFalse(machine.isInFinalState(), "Machine is in final state");
    assertFalse(machine.isInErrorState(), "Machine is in error state");
    assertEquals(stepsBeforeError, machine.getStepsBeforeError(), "Wrong number of steps before error");
  }

  @Test
  void getTokenType() {
    assertEquals(TokenType.NUMBER, machine.getTokenType());
  }
}
