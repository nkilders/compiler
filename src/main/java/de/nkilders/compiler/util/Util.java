package de.nkilders.compiler.util;

public class Util {
    public static LineCol calculateLineAndCol(String text, int pos) {
        text = text.substring(0, pos);

        int line = text.chars().filter(c -> c == '\n').map(_ -> 1).sum() + 1;
        int col = pos - text.lastIndexOf('\n');
        
        return new LineCol(line, col);
    }

    public record LineCol(int line, int col) {
    }
}
