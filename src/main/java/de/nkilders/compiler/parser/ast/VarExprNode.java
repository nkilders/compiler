package de.nkilders.compiler.parser.ast;

public class VarExprNode extends ExprNode {
    private String varName;

    public VarExprNode(String varName) {
        this.varName = varName;
    }

    @Override
    public double eval() {
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }
    
    public String getVarName() {
        return varName;
    }
}
