package de.nkilders.compiler.lexer.machines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.nkilders.compiler.TokenType;

class WhitespaceMachineTest {
  private WhitespaceMachine machine;

  @BeforeEach
  void beforeEach() {
    machine = new WhitespaceMachine();
  }

  @ParameterizedTest
  @ValueSource(strings = { " ", "\t", "\n" })
  void happyPath(String input) {
    machine.processText(input);

    assertTrue(machine.isInFinalState(), "Machine is not in final state");
    assertFalse(machine.isInErrorState(), "Machine is in error state");
    assertEquals(input.length(), machine.getStepsBeforeError(), "Wrong number of steps before error");
  }

  @ParameterizedTest
  @ValueSource(strings = { "a", "1", "." })
  void errorAndNotFinalState(String input) {
    machine.processText(input);

    assertFalse(machine.isInFinalState(), "Machine is in final state");
    assertTrue(machine.isInErrorState(), "Machine is not in error state");
    assertEquals(1, machine.getStepsBeforeError(), "Wrong number of steps before error");
  }

  @Test
  void getTokenType() {
    assertEquals(TokenType.WHITESPACE, machine.getTokenType());
  }
}
