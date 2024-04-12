package de.nkilders.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.BooleanValue;
import de.nkilders.compiler.interpreter.values.NullValue;

public class RootNode extends AstNode {
    private final List<StmtNode> statements;
    private final Environment environment;

    public RootNode() {
        this.statements = new ArrayList<>();
        this.environment = new Environment();

        declareGlobalVariables();
    }

    public void addStatement(StmtNode stmt) {
        this.statements.add(stmt);
    }

    public void run() {
        statements.stream().forEach(stmt -> stmt.exec(environment));
    }

    private void declareGlobalVariables() {
        environment.declareVariable("true", true, new BooleanValue(true));
        environment.declareVariable("false", true, new BooleanValue(false));
        environment.declareVariable("null", true, new NullValue());
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return "RootNode [statements=" + statements + "]";
    }
}
