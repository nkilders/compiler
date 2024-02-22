package de.nkilders.compiler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nkilders.compiler.lexer.Lexer;
import de.nkilders.compiler.lexer.LexerImpl;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Lexer lexer = new LexerImpl();
        String input = " //c\n ";

        input = "+-/*()[]{}";

        List<Token> tokens = lexer.tokenize(input);
        tokens.forEach(t -> LOGGER.info(t.toString()));
    }
}
