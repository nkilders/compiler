package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class VarExprNode extends ExprNode {
    private String varName;

    public VarExprNode(String varName) {
        this.varName = varName;
    }

    @Override
    public RuntimeValue<?> eval(Environment env) {
        return env.readVariable(varName);
    }
    
    public String getVarName() {
        return varName;
    }
}
