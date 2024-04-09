package de.nkilders.compiler.parser.ast;

import static de.nkilders.compiler.TokenType.COMMA;
import static de.nkilders.compiler.TokenType.ELSE;
import static de.nkilders.compiler.TokenType.MINUS;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.PLUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    BooleanExprNode booleanExprNode1;

    @Mock
    NumericExprNode numericExprNode1;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void eval_bool_not() {
        when(booleanExprNode1.eval()).thenReturn(new BooleanValue(true));

        UnaryExprNode node = new UnaryExprNode(NOT, booleanExprNode1);

        RuntimeValue<?> result = node.eval();

        assertEquals(false, result.getValue());
    }
    
    @Test
    void eval_bool_err() {
        when(booleanExprNode1.eval()).thenReturn(new BooleanValue(true));

        UnaryExprNode node = new UnaryExprNode(ELSE, booleanExprNode1);

        assertThrows(UnsupportedOperationException.class, () -> node.eval());
    }
    
    @Test
    void eval_num_plus() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));

        UnaryExprNode node = new UnaryExprNode(PLUS, numericExprNode1);

        RuntimeValue<?> result = node.eval();

        assertEquals(1.0, result.getValue());
    }
    
    @Test
    void eval_num_minus() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));

        UnaryExprNode node = new UnaryExprNode(MINUS, numericExprNode1);

        RuntimeValue<?> result = node.eval();

        assertEquals(-1.0, result.getValue());
    }
    
    @Test
    void eval_num_err() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));

        UnaryExprNode node = new UnaryExprNode(COMMA, numericExprNode1);

        assertThrows(UnsupportedOperationException.class, () -> node.eval());
    }

}
