package de.nkilders.compiler.parser;

import java.util.List;

import de.nkilders.compiler.Token;
import de.nkilders.compiler.parser.ast.RootNode;

public interface Parser {
    RootNode parse(List<Token> tokens);
}
