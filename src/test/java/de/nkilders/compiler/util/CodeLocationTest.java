package de.nkilders.compiler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CodeLocationTest {

  @Test
  void fromTextIndex() {
    CodeLocation location = CodeLocation.fromTextIndex("\nabc", 3);

    assertEquals(2, location.line(), "Wrong line");
    assertEquals(3, location.column(), "Wrong column");
  }

  @Test
  void fromTextIndex_posZero() {
    CodeLocation location = CodeLocation.fromTextIndex("", 0);

    assertEquals(1, location.line(), "Wrong line");
    assertEquals(1, location.column(), "Wrong column");
  }

  @Test
  void fromTextIndex_textNull() {
    assertThrows(NullPointerException.class, () -> {
      CodeLocation.fromTextIndex(null, 0);
    });
  }

  @Test
  void fromTextIndex_posNegative() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      CodeLocation.fromTextIndex("", -1);
    });
  }
}
