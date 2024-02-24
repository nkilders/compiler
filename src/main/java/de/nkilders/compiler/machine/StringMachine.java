package de.nkilders.compiler.machine;

public class StringMachine extends StateMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var start = state("start");
        var content = state("content");
        var escape = state("escape");
        var end = state("end", true);
        var err = errorState();

        init.addTransition(start, "\"")
            .setFallbackTransitionState(err);

        start.addTransition(escape, "\\\\")
             .addTransition(content, ".")
             .setFallbackTransitionState(err);

        content.addTransition(escape, "\\\\")
               .addTransition(end, "\"")
               .addTransition(content, ".")
               .setFallbackTransitionState(err);

        escape.addTransition(content, "[\\\\\"]")
              .setFallbackTransitionState(err);

        end.setFallbackTransitionState(err);
    }
    
}
