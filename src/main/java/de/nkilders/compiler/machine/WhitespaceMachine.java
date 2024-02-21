package de.nkilders.compiler.machine;

public class WhitespaceMachine extends StateMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var whitespace = state("whitespace", true);
        var err = errorState();

        init.addTransition(whitespace, "\\s")
            .setFallbackTransitionState(err);
        
        whitespace.addTransition(whitespace, "\\s")
                  .setFallbackTransitionState(err);
    }
}
