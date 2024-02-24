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
    MULTIPLY,
    DIVIDE,
    EQUALS,
    ASSIGN,
    GT,
    GTE,
    LT,
    LTE,
    SEMICOLON,
    COMMA,
    
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
