package de.nkilders.compiler.util;

public record CodeLocation(int line, int column) {
  public static CodeLocation fromTextIndex(String text, int index) {
    text = text.substring(0, index);

    int line = (int) text.chars()
        .filter(c -> c == '\n')
        .count() + 1;
    int column = index - text.lastIndexOf('\n');

    return new CodeLocation(line, column);
  }
}
