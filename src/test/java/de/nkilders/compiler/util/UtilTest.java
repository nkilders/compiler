package de.nkilders.compiler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.nkilders.compiler.util.Util.LineCol;

class UtilTest {

  @Test
  void calculateLineAndCol() {
    LineCol lineCol = Util.calculateLineAndCol("\nabc", 3);

    assertEquals(2, lineCol.line());
    assertEquals(3, lineCol.col());
  }

  @Test
  void calculateLineAndCol_posZero() {
    LineCol lineCol = Util.calculateLineAndCol("", 0);

    assertEquals(1, lineCol.line());
    assertEquals(1, lineCol.col());
  }

  @Test
  void calculateLineAndCol_textNull() {
    assertThrows(NullPointerException.class, () -> {
      Util.calculateLineAndCol(null, 0);
    });
  }

  @Test
  void calculateLineAndCol_posNegative() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      Util.calculateLineAndCol("", -1);
    });
  }
}
