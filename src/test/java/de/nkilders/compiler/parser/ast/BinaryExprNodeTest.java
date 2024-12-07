package de.nkilders.compiler.parser.ast;

import static de.nkilders.compiler.TokenType.IF;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.PLUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nkilders.compiler.interpreter.values.NumberValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;
import de.nkilders.compiler.interpreter.values.StringValue;

class BinaryExprNodeTest {

  @Mock
  ExprNode exprNode1;

  @Mock
  ExprNode exprNode2;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void eval_evalNumNum_plus() {
    when(exprNode1.eval(any())).thenReturn(new NumberValue(1));
    when(exprNode2.eval(any())).thenReturn(new NumberValue(2));

    BinaryExprNode node = new BinaryExprNode(exprNode1, exprNode2, PLUS);

    RuntimeValue<?> val = node.eval(null);

    assertEquals(3.0, val.getValue());
  }

  @Test
  void eval_evalNumNum_err() {
    when(exprNode1.eval(any())).thenReturn(new NumberValue(1));
    when(exprNode2.eval(any())).thenReturn(new NumberValue(2));

    BinaryExprNode node = new BinaryExprNode(exprNode1, exprNode2, NOT);

    assertThrows(UnsupportedOperationException.class, () -> node.eval(null));
  }

  @Test
  void eval_evalStr_plus() {
    when(exprNode1.eval(any())).thenReturn(new NumberValue(1));
    when(exprNode2.eval(any())).thenReturn(new StringValue("Hello"));

    BinaryExprNode node = new BinaryExprNode(exprNode1, exprNode2, PLUS);

    RuntimeValue<?> val = node.eval(null);

    assertEquals("1.0Hello", val.getValue());
  }

  @Test
  void eval_evalStr_err() {
    when(exprNode1.eval(any())).thenReturn(new NumberValue(1));
    when(exprNode2.eval(any())).thenReturn(new StringValue("Hello"));

    BinaryExprNode node = new BinaryExprNode(exprNode1, exprNode2, IF);

    assertThrows(UnsupportedOperationException.class, () -> node.eval(null));
  }
}
