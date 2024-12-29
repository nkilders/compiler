package de.nkilders.compiler;

import de.nkilders.compiler.util.CodeLocation;

public record Token(TokenType type, String content, CodeLocation location) {
}
