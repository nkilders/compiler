package de.nkilders.compiler.lexer;

import java.util.List;

import de.nkilders.compiler.Token;

public interface Lexer {
    List<Token> tokenize(String input);
}
