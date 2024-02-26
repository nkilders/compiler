package de.nkilders.compiler.lexer;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.util.StateMachine;

public abstract class LexerMachine extends StateMachine {

    public LexerMachine() {
        super();
    }

    public LexerMachine(boolean initialize) {
        super(initialize);
    }

    public abstract TokenType getTokenType();
}
