package de.nkilders.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nkilders.compiler.lexer.Lexer;
import de.nkilders.compiler.lexer.LexerImpl;

public class Main {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    Lexer lexer = new LexerImpl();
    String input = loadScript("test");

    List<Token> tokens = lexer.tokenize(input);
    tokens.forEach(t -> LOGGER.info(t.toString()));
  }

  private static String loadScript(String filename) throws IOException {
    ClassLoader classloader = Thread.currentThread()
        .getContextClassLoader();
    String pathStr = classloader.getResource("scripts/" + filename)
        .getPath();
    Path path = Paths.get(pathStr);

    return Files.readString(path);
  }
}
