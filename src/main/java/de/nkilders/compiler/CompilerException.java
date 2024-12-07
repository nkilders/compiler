package de.nkilders.compiler;

import de.nkilders.compiler.util.Util.LineCol;

public class CompilerException extends RuntimeException {
  private final int line;
  private final int col;

  public CompilerException(String message, int line, int col) {
    super(buildMessage(message, line, col));

    this.line = line;
    this.col = col;
  }

  public CompilerException(String message, LineCol lineCol) {
    this(message, lineCol.line(), lineCol.col());
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
