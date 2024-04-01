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
    MODULO,
    EQUALS,
    ASSIGN,
    GT,
    GTE,
    LT,
    LTE,
    SEMICOLON,
    COMMA,
    AND,
    OR,
    
    // Static + Identifier-like
    FUNCTION,
    LET,
    CONST,
    IF,
    ELSE,
    WHILE,
    TRUE,
    FALSE,

    // Dynamic
    IDENTIFIER,
    STRING,
    LINE_COMMENT,
    MULTI_LINE_COMMENT,
    WHITESPACE,
    NUMBER,

    EOF,
}
