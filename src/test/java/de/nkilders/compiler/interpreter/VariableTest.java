package de.nkilders.compiler.interpreter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class VariableTest {
    
    @Test
    void setValue_constThrowsError() {
        Variable v = new Variable("", true);

        assertThrows(VarException.class, () -> v.setValue(null));
    }

}
