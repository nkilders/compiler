package de.nkilders.compiler;

import de.nkilders.compiler.util.Util.LineCol;

public record Token(TokenType type, String content, LineCol pos) {
}
