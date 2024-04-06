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
    NOT,
    
    // Static + Identifier-like
    FUNCTION,
    LET,
    CONST,
    IF,
    ELSE,
    WHILE,
    TRUE,
    FALSE,
    NULL,

    // Dynamic
    IDENTIFIER,
    STRING,
    LINE_COMMENT,
    MULTI_LINE_COMMENT,
    WHITESPACE,
    NUMBER,

    EOF,
}
