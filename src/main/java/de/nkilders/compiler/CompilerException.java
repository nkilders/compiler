package de.nkilders.compiler;

import de.nkilders.compiler.util.CodeLocation;

public class CompilerException extends RuntimeException {
  private final int line;
  private final int col;

  public CompilerException(String message, int line, int col) {
    super(buildMessage(message, line, col));

    this.line = line;
    this.col = col;
  }

  public CompilerException(String message, CodeLocation location) {
    this(message, location.line(), location.column());
  }

  public int getLine() {
    return line;
  }

  public int getCol() {
    return col;
  }

  private static String buildMessage(String message, int line, int col) {
    return String.format("%s (line %d, col %d)", message, line, col);
  }
}
