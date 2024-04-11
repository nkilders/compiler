package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

public class DeclareStmtNode extends StmtNode {
    private final String varName;
    private final boolean isConst;
    private final ExprNode expr;

    public DeclareStmtNode(String varName, boolean isConst, ExprNode expr) {
        this.varName = varName;
        this.isConst = isConst;
        this.expr = expr;
    }

    public DeclareStmtNode(String varName, boolean isConst) {
        this(varName, isConst, null);
    }

    @Override
    public void exec(Environment env) {
        RuntimeValue<?> value = null;

        if(expr != null) {
            value = expr.eval(env);
        }

        env.declareVariable(varName, isConst, value);
    }
}
