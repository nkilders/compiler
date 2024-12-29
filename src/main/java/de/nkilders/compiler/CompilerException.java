package de.nkilders.compiler;

import de.nkilders.compiler.util.CodeLocation;

public class CompilerException extends RuntimeException {
  private final int line;
  private final int column;

  public CompilerException(String message, int line, int column) {
    super(buildMessage(message, line, column));

    this.line = line;
    this.column = column;
  }

  public CompilerException(String message, CodeLocation location) {
    this(message, location.line(), location.column());
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

  private static String buildMessage(String message, int line, int column) {
    return String.format("%s (line %d, column %d)", message, line, column);
  }
}
