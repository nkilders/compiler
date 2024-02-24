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
    EQUALS,
    // MULTI CHAR
    STRING,
    IDENTIFIER,
    LINE_COMMENT,
    WHITESPACE,
    NUMBER,
    // RESERVED KEYWORDS
    FUNCTION,
    LET,
    CONST,
    IF,
    ELSE,
    WHILE,
}
