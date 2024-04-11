package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.interpreter.Environment;

public class RootNode extends AstNode {
    private final List<StmtNode> statements;
    private final Environment environment;

    public RootNode() {
        this.statements = new ArrayList<>();
        this.environment = new Environment();
    }

    public void addStatement(StmtNode stmt) {
        this.statements.add(stmt);
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void run() {
        statements.stream().forEach(stmt -> stmt.exec(environment));
    }

    @Override
    public String toString() {
        return "RootNode [statements=" + statements + "]";
    }
}
