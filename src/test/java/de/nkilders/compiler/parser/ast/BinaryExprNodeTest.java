package de.nkilders.compiler.parser.ast;

import static de.nkilders.compiler.TokenType.IF;
import static de.nkilders.compiler.TokenType.NOT;
import static de.nkilders.compiler.TokenType.PLUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    NumericExprNode numericExprNode1;

    @Mock
    NumericExprNode numericExprNode2;

    @Mock
    StringExprNode stringExprNode1;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void eval_evalNumNum_plus() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));
        when(numericExprNode2.eval()).thenReturn(new NumberValue(2));

        BinaryExprNode node = new BinaryExprNode(numericExprNode1, numericExprNode2, PLUS);

        RuntimeValue<?> val = node.eval();

        assertEquals(3.0, val.getValue());
    }

    @Test
    void eval_evalNumNum_err() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));
        when(numericExprNode2.eval()).thenReturn(new NumberValue(2));

        BinaryExprNode node = new BinaryExprNode(numericExprNode1, numericExprNode2, NOT);

        assertThrows(UnsupportedOperationException.class, () -> node.eval());
    }

    @Test
    void eval_evalStr_plus() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));
        when(stringExprNode1.eval()).thenReturn(new StringValue("Hello"));

        BinaryExprNode node = new BinaryExprNode(numericExprNode1, stringExprNode1, PLUS);

        RuntimeValue<?> val = node.eval();

        assertEquals("1.0Hello", val.getValue());
    }

    @Test
    void eval_evalStr_err() {
        when(numericExprNode1.eval()).thenReturn(new NumberValue(1));
        when(stringExprNode1.eval()).thenReturn(new StringValue("Hello"));

        BinaryExprNode node = new BinaryExprNode(numericExprNode1, stringExprNode1, IF);
        
        assertThrows(UnsupportedOperationException.class, () -> node.eval());
    }
}