package de.nkilders.compiler;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.nkilders.compiler.interpreter.Environment;
import de.nkilders.compiler.lexer.Lexer;
import de.nkilders.compiler.lexer.LexerImpl;
import de.nkilders.compiler.parser.Parser;
import de.nkilders.compiler.parser.ParserImpl;
import de.nkilders.compiler.parser.ast.RootNode;

public class CLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLI.class);
    
    private final Lexer lexer;
    private final Parser parser;

    private final Environment environment;

    private final Gson gson;

    public CLI() {
        lexer = new LexerImpl();
        parser = new ParserImpl();

        environment = new Environment();

        gson = new GsonBuilder().setPrettyPrinting().create();

        start();
    }

    private void start() {
        try(Scanner scanner = new Scanner(System.in)) {
            String line;
            do {
                System.out.print("\n> "); // NOSONAR
                line = scanner.nextLine();
                System.out.println(); // NOSONAR

                process(line);
            } while (line != null);
        }
    }

    private void process(String input) {
        List<Token> tokens = lexer.tokenize(input);
        
        String tokensStr = tokens.stream().map(Token::type).toList().toString();
        LOGGER.debug("Tokens: {}", tokensStr);

        RootNode ast = parser.parse(tokens);

        String astStr = gson.toJson(ast);
        LOGGER.debug("AST: {}", astStr);

        try {
            ast.run(environment);
        } catch(RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CLI();
    }
}
