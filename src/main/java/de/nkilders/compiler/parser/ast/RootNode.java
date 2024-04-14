package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.interpreter.Environment;

public class RootNode extends AstNode {
    private final List<StmtNode> statements;

    public RootNode() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(StmtNode stmt) {
        this.statements.add(stmt);
    }

    public void run(Environment environment) {
        statements.stream().forEach(stmt -> stmt.exec(environment));
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "RootNode [statements=" + statements + "]";
    }
}
