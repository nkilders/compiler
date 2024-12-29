package de.nkilders.compiler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UtilTest {

  @Test
  void calculateLineAndCol() {
    CodeLocation location = CodeLocation.fromTextIndex("\nabc", 3);

    assertEquals(2, location.line());
    assertEquals(3, location.column());
  }

  @Test
  void calculateLineAndCol_posZero() {
    CodeLocation location = CodeLocation.fromTextIndex("", 0);

    assertEquals(1, location.line());
    assertEquals(1, location.column());
  }

  @Test
  void calculateLineAndCol_textNull() {
    assertThrows(NullPointerException.class, () -> {
      CodeLocation.fromTextIndex(null, 0);
    });
  }

  @Test
  void calculateLineAndCol_posNegative() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      CodeLocation.fromTextIndex("", -1);
    });
  }
}
