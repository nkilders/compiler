package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.interpreter.Environment;

public class BlockStmtNode extends StmtNode {
    private final List<StmtNode> statements;
    private Environment environment;

    public BlockStmtNode() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(StmtNode statement) {
        this.statements.add(statement);
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    @Override
    public void exec(Environment env) {
        if(environment == null) {
            environment = new Environment(env);
        }

        statements.forEach(stmt -> stmt.exec(environment));
    }

    @Override
    public String toString() {
        return "BlockStmtNode [statements=" + statements + "]";
    }
}
