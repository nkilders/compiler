package de.nkilders.compiler;

public enum TokenType {
    // SINGLE CHAR
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,
    PLUS,
    MINUS,
    MUL,
    DIV,
    // MULTI CHAR
    STRING,
    IDENTIFIER,
    LINE_COMMENT,
    WHITESPACE,
    // RESERVED KEYWORDS
    FUNCTION,
    LET,
    CONST,
    IF,
    ELSE,
    WHILE,
}
