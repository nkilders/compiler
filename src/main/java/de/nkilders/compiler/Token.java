package de.nkilders.compiler;

public record Token(TokenType type, String content, int line, int col) {
}
