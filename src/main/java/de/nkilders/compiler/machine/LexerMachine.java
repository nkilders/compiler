package de.nkilders.compiler.machine;

import de.nkilders.compiler.TokenType;

public abstract class LexerMachine extends StateMachine {
    public abstract TokenType getTokenType();
}
