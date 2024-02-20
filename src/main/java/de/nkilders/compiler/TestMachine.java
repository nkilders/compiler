package de.nkilders.compiler;

public class TestMachine extends StateMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState("init");
        var digit = state("digit", true);
        var letter = state("letter", true);
        var err = errorState();

        final String digitPattern = "[0-9]";
        final String letterPattern = "[a-zA-Z]";

        init.addTransition(digit, digitPattern)
            .addTransition(letter, letterPattern)
            .setFallbackTransitionState(err);
        
        digit.addTransition(digit, digitPattern)
             .addTransition(letter, letterPattern)
             .setFallbackTransitionState(err);

        letter.addTransition(digit, digitPattern)
              .addTransition(letter, letterPattern)
              .setFallbackTransitionState(err);
    }
}
