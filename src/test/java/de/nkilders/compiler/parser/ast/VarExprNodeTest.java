package de.nkilders.compiler.parser.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

class VarExprNodeTest {

    @Mock
    Environment env;

    @Mock
    RuntimeValue<String> val;
    
    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void t() {
        final String varName = "myVar";
        final String varValue = "myVal";

        ExprNode node = new VarExprNode(varName);
        
        when(env.readVariable(varName)).thenReturn(val);
        when(val.getValue()).thenReturn(varValue);

        RuntimeValue<?> result = node.eval(env);

        assertEquals(result.getValue(), varValue);
    }

}
