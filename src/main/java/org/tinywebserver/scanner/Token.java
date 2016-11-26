package org.tinywebserver.scanner;

/**
 * Created by user on 11/24/2016.
 */
public class Token {

    private String value;

    private TokenType tokenType;

    public Token(String value, TokenType tokenType) {
        this.value = value;
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
