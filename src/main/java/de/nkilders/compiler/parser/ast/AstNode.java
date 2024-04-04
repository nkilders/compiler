package de.nkilders.compiler.parser.ast;

import de.nkilders.compiler.CLI;

public abstract class AstNode {
    /**
     * Used for node identification when printing the AST in {@link CLI}
     */
    private final String nodeType = getClass().getSimpleName();
}
