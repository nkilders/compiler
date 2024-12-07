package de.nkilders.compiler.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StateMachine {
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass()); // NOSONAR

  private State initialState;
  private State currentState;
  private State errorState;
  private int stepsBeforeError;
  private boolean inFinalStateBeforeError;

  protected StateMachine(boolean initialize) {
    this.initialState = null;
    this.currentState = null;
    this.errorState = null;
    this.stepsBeforeError = 0;
    this.inFinalStateBeforeError = false;

    if (initialize) {
      this.init();
    }
  }

  protected StateMachine() {
    this(true);
  }

  /**
   * Initializes all states and transitions of the machine
   */
  protected abstract void initStatesAndTransitions();

  public void init() {
    this.initStatesAndTransitions();
    this.reset();
  }

  /**
   * Processes a character and performs the transition to the next state
   * 
   * @param c character to process
   */
  public void step(char c) {
    if (currentState == errorState) {
      return;
    }

    stepsBeforeError++;

    String str = String.valueOf(c);

    for (Transition t : currentState.transitions) {
      if (str.matches(t.on())) {
        LOGGER.debug("{}({}) -> {}", currentState.name, c, t.to().name);
        updateState(t.to());
        return;
      }
    }

    if (currentState.fallbackTransitionState == null) {
      LOGGER.warn("No transition for input \"{}\" on state \"{}\" and no fallback state either", c, currentState.name);
      LOGGER.debug("{}({}) -> {} (no fallback)", currentState.name, c, currentState.name);
      return;
    }

    LOGGER.debug("{}({}) -> {} (fallback)", currentState.name, c, currentState.fallbackTransitionState.name);
    updateState(currentState.fallbackTransitionState);
  }

  /**
   * Processes a whole text character by character and performs the transitions to
   * the next states
   * 
   * @param s text to process
   */
  public void processText(String s) {
    for (char c : s.toCharArray()) {
      this.step(c);
    }
  }

  /**
   * Resets the current state back to the initial state
   */
  public void reset() {
    this.currentState = this.initialState;
    this.stepsBeforeError = 0;
    this.inFinalStateBeforeError = false;
  }

  /**
   * @return {@code true} if the current state is a final state
   */
  public boolean isInFinalState() {
    return currentState.isFinal();
  }

  public boolean isInErrorState() {
    return currentState == errorState;
  }

  public State getInitialState() {
    return initialState;
  }

  public State getCurrentState() {
    return currentState;
  }

  public State getErrorState() {
    return errorState;
  }

  public int getStepsBeforeError() {
    return stepsBeforeError;
  }

  public boolean wasInFinalStateBeforeError() {
    return inFinalStateBeforeError;
  }

  /**
   * Creates and returns a new non-final state and sets it as the initial state of
   * the machine
   * 
   * @return the newly created state
   */
  protected State initialState() {
    return initialState(false);
  }

  /**
   * Creates and returns a new state and sets it as the initial state of the
   * machine
   * 
   * @param isFinal whether the state is a final state or not
   * @return the newly created state
   */
  protected State initialState(boolean isFinal) {
    State state = state("init", isFinal);

    this.initialState = state;

    return state;
  }

  /**
   * Creates and returns a new non-final state
   * 
   * @param name name of the state, only used for console logs
   * @return the newly created state
   */
  protected State state(String name) {
    return state(name, false);
  }

  /**
   * Creates and returns a new state
   * 
   * @param name    name of the state, only used for console logs
   * @param isFinal whether the state is a final state or not
   * @return the newly created state
   */
  protected State state(String name, boolean isFinal) {
    return new State(name, isFinal);
  }

  /**
   * Creates and returns a new non-final state which acts as the error state of
   * the machine
   * 
   * @return the newly created state
   */
  protected State errorState() {
    State state = state("error", false);
    state.setFallbackTransitionState(state);

    this.errorState = state;

    return state;
  }

  private void updateState(State newState) {
    if (currentState.isFinal() && newState == errorState) {
      inFinalStateBeforeError = true;
    }

    currentState = newState;
  }

  protected static class State {
    private String name;
    private boolean isFinal;
    private List<Transition> transitions;
    private State fallbackTransitionState;

    /**
     * @param name    name of the state, only used for console logs
     * @param isFinal whether the state is a final state or not
     */
    public State(String name, boolean isFinal) {
      this.name = name;
      this.isFinal = isFinal;
      this.transitions = new ArrayList<>();
      this.fallbackTransitionState = null;
    }

    /**
     * @param to state to transition to
     * @param on RegEx pattern which decides if the transition will be performed
     * @return this
     */
    public State addTransition(State to, String on) {
      this.transitions.add(new Transition(to, on));
      return this;
    }

    /**
     * @param to state to go to, if no other transition was performed
     * @return this
     */
    public State setFallbackTransitionState(State to) {
      this.fallbackTransitionState = to;
      return this;
    }

    public String getName() {
      return name;
    }

    public boolean isFinal() {
      return isFinal;
    }
  }

  protected record Transition(State to, String on) {
  }
}
