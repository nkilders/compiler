package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class RootNode extends AstNode {
    private List<StmtNode> statements;

    public RootNode() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(StmtNode stmt) {
        this.statements.add(stmt);
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    public void run() {
        statements.stream().forEach(StmtNode::exec);
    }

    @Override
    public String toString() {
        return "RootNode [statements=" + statements + "]";
    }
}
