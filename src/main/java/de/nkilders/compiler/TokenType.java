package de.nkilders.compiler;

public enum TokenType {
    // Static
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
    
    // Static + Identifier-like
    FUNCTION,
    LET,
    CONST,
    IF,
    ELSE,
    WHILE,

    // Dynamic
    IDENTIFIER,
    STRING,
    LINE_COMMENT,
    WHITESPACE,
    NUMBER,
}
