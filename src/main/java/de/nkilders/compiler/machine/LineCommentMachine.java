package de.nkilders.compiler.machine;

public class LineCommentMachine extends StateMachine {

    @Override
    protected void initStatesAndTransitions() {
        var init = initialState();
        var firstSlash = state("first slash");
        var secondSlash = state("second slash", true);
        var content = state("content", true);
        var err = errorState();

        init.addTransition(firstSlash, "/")
            .setFallbackTransitionState(err);

        firstSlash.addTransition(secondSlash, "/")
                  .setFallbackTransitionState(err);

        secondSlash.addTransition(content, ".")
                   .setFallbackTransitionState(err);

        content.addTransition(content, ".")
               .setFallbackTransitionState(err);
    }
}
