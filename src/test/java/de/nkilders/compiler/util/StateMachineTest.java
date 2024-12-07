package de.nkilders.compiler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.nkilders.compiler.util.StateMachine.State;

class StateMachineTest {
  StateMachine machine;

  @BeforeEach
  void beforeEach() {
    machine = new StateMachine() {
      @Override
      protected void initStatesAndTransitions() {
        // States and transitions will be initialized in each test
      }
    };
  }

  @Test
  void step_normalTransition() {
    var init = machine.initialState();
    var state = machine.state("state");

    init.addTransition(state, "a");

    machine.reset();

    assertEquals(init, machine.getCurrentState());
    assertEquals(0, machine.getStepsBeforeError());

    machine.step('a');

    assertEquals(state, machine.getCurrentState());
    assertEquals(1, machine.getStepsBeforeError());
  }

  @Test
  void step_fallbackTransition() {
    var init = machine.initialState();
    var state = machine.state("state");

    init.setFallbackTransitionState(state);

    machine.reset();

    assertEquals(init, machine.getCurrentState());
    assertEquals(0, machine.getStepsBeforeError());

    machine.step('a');

    assertEquals(state, machine.getCurrentState());
    assertEquals(1, machine.getStepsBeforeError());
  }

  @Test
  void step_missingFallbackTransition() {
    var init = machine.initialState();

    machine.reset();

    assertEquals(init, machine.getCurrentState());
    assertEquals(0, machine.getStepsBeforeError());

    machine.step('a');

    assertEquals(init, machine.getCurrentState());
    assertEquals(1, machine.getStepsBeforeError());
  }

  @Test
  void processText() {
    StateMachine spyMachine = spy(machine);
    doNothing().when(spyMachine)
        .step(anyChar());

    spyMachine.processText("122");

    verify(spyMachine, times(1)).step('1');
    verify(spyMachine, times(2)).step('2');
  }

  @Test
  void initialState() {
    assertNull(machine.getInitialState());

    State state = machine.initialState();

    assertNotNull(state);
    assertEquals(state, machine.getInitialState());
    assertEquals("init", state.getName());
    assertFalse(state.isFinal());
  }

  @Test
  void errorState() {
    assertNull(machine.getErrorState());

    State state = machine.errorState();

    assertNotNull(state);
    assertEquals(state, machine.getErrorState());
    assertEquals("error", state.getName());
    assertFalse(state.isFinal());
  }

  @Test
  void state() {
    final String stateName = "my state";

    State state = machine.state(stateName);

    assertNotNull(state);
    assertEquals(stateName, state.getName());
    assertFalse(state.isFinal());
  }

}
