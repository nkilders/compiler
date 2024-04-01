package de.nkilders.compiler.parser.ast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExprNode extends StmtNode {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExprNode.class);
    
    public abstract double eval();

    @Override
    public void exec() {
        double value = eval();
        LOGGER.info("Orphaned expression evaluated to {}", value);
    }
}
