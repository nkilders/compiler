package de.nkilders.compiler.parser.ast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.interpreter.values.RuntimeValue;

public abstract class ExprNode extends StmtNode {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExprNode.class);
    
    @SuppressWarnings("rawtypes")
    public abstract RuntimeValue eval(Environment env);

    @Override
    public void exec(Environment env) {
        RuntimeValue<?> value = eval(env);
        LOGGER.info("Orphaned expression evaluated to {}", value);
    }
}
