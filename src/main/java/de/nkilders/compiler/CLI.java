package de.nkilders.compiler;

import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.nkilders.compiler.lexer.Lexer;
import de.nkilders.compiler.lexer.LexerImpl;
import de.nkilders.compiler.parser.Parser;
import de.nkilders.compiler.parser.ParserImpl;
import de.nkilders.compiler.parser.ast.RootNode;

public class CLI {
    
    public static void main(String[] args) {
        Lexer lexer = new LexerImpl();
        Parser parser = new ParserImpl();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(Scanner scanner = new Scanner(System.in)) {
            String line;
            do {
                System.out.print("\n> "); // NOSONAR
                line = scanner.nextLine();

                List<Token> tokens = lexer.tokenize(line);
                RootNode ast = parser.parse(tokens);

                System.out.println(gson.toJson(ast)); // NOSONAR
                ast.run();
            } while (line != null);
        }  
    }
}
