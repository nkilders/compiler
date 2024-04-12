package de.nkilders.compiler.parser.ast;

import static de.nkilders.compiler.TokenType.COMMA;
import static de.nkilders.compiler.TokenType.ELSE;
import static de.nkilders.compiler.TokenType.MINUS;
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

import de.nkilders.compiler.interpreter.values.BooleanValue;
import de.nkilders.compiler.interpreter.values.NumberValue;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

class UnaryExprNodeTest {

    @Mock
    ExprNode exprNode1;

    @Mock
    ExprNode exprNode2;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void eval_bool_not() {
        when(exprNode1.eval(any())).thenReturn(new BooleanValue(true));

        UnaryExprNode node = new UnaryExprNode(NOT, exprNode1);

        RuntimeValue<?> result = node.eval(null);

        assertEquals(false, result.getValue());
    }
    
    @Test
    void eval_bool_err() {
        when(exprNode1.eval(any())).thenReturn(new BooleanValue(true));

        UnaryExprNode node = new UnaryExprNode(ELSE, exprNode1);

        assertThrows(UnsupportedOperationException.class, () -> node.eval(null));
    }
    
    @Test
    void eval_num_plus() {
        when(exprNode2.eval(any())).thenReturn(new NumberValue(1));

        UnaryExprNode node = new UnaryExprNode(PLUS, exprNode2);

        RuntimeValue<?> result = node.eval(null);

        assertEquals(1.0, result.getValue());
    }
    
    @Test
    void eval_num_minus() {
        when(exprNode2.eval(any())).thenReturn(new NumberValue(1));

        UnaryExprNode node = new UnaryExprNode(MINUS, exprNode2);

        RuntimeValue<?> result = node.eval(null);

        assertEquals(-1.0, result.getValue());
    }
    
    @Test
    void eval_num_err() {
        when(exprNode2.eval(any())).thenReturn(new NumberValue(1));

        UnaryExprNode node = new UnaryExprNode(COMMA, exprNode2);

        assertThrows(UnsupportedOperationException.class, () -> node.eval(null));
    }

}
