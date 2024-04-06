package de.nkilders.compiler;

import static de.nkilders.compiler.TokenType.CONST;
import static de.nkilders.compiler.TokenType.ELSE;
import static de.nkilders.compiler.TokenType.FALSE;
import static de.nkilders.compiler.TokenType.FUNCTION;
import static de.nkilders.compiler.TokenType.IF;
import static de.nkilders.compiler.TokenType.LET;
import static de.nkilders.compiler.TokenType.NULL;
import static de.nkilders.compiler.TokenType.TRUE;
import static de.nkilders.compiler.TokenType.WHILE;

import java.util.HashMap;
import java.util.Map;

public class ReservedKeyword {
    private static final Map<String, TokenType> KEYWORDS;

    static {
        KEYWORDS = new HashMap<>();
        
        keyword("function", FUNCTION);
        keyword("let", LET);
        keyword("const", CONST);
        keyword("if", IF);
        keyword("else", ELSE);
        keyword("while", WHILE);
        keyword("true", TRUE);
        keyword("false", FALSE);
        keyword("null", NULL);
    }

    private static void keyword(String key, TokenType type) {
        KEYWORDS.put(key, type);
    }

    public static TokenType get(String text) {
        return KEYWORDS.get(text);
    }

    /**
     * Private constructor to hide the implicit public one
     */
    private ReservedKeyword() {}
}
