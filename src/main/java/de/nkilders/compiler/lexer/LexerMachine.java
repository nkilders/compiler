package de.nkilders.compiler.lexer;

import de.nkilders.compiler.TokenType;
import de.nkilders.compiler.util.StateMachine;

public abstract class LexerMachine extends StateMachine {
    public abstract TokenType getTokenType();
}
