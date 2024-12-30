package de.nkilders.compiler.lexer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.nkilders.compiler.CompilerException;
import de.nkilders.compiler.Token;
import de.nkilders.compiler.TokenType;

class LexerImplTest {
  private LexerImpl lexer;

  @BeforeEach
  void beforeEach() {
    lexer = new LexerImpl();
  }

  @Test
  void tokenize() {
    String input = """
        const 123 "abc" true,()[]{}
        """;

    List<Token> tokens = lexer.tokenize(input);

    // @formatter:off
    assertThat(tokens).extracting(Token::type, t -> t.location().column())
        .containsExactly(
          tuple(TokenType.CONST, 1),
          tuple(TokenType.WHITESPACE, 6),
          tuple(TokenType.NUMBER, 7),
          tuple(TokenType.WHITESPACE, 10),
          tuple(TokenType.STRING, 11),
          tuple(TokenType.WHITESPACE, 16),
          tuple(TokenType.IDENTIFIER, 17),
          tuple(TokenType.COMMA, 21),
          tuple(TokenType.LPAREN, 22),
          tuple(TokenType.RPAREN, 23),
          tuple(TokenType.LBRACKET, 24),
          tuple(TokenType.RBRACKET, 25),
          tuple(TokenType.LBRACE, 26),
          tuple(TokenType.RBRACE, 27),
          tuple(TokenType.WHITESPACE, 28),
          tuple(TokenType.EOF, 1));
    // @formatter:on
  }

  @Test
  void unprocessableCharacter() {
    assertThrows(CompilerException.class, () -> {
      lexer.tokenize("\u0000");
    });
  }
}
