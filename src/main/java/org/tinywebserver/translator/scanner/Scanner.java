package org.tinywebserver.translator.scanner;

import java.io.IOException;
import java.util.List;


public abstract class Scanner {

    private Token currentToken;

    private Source source;

    public Scanner(Source source) {
        this.source = source;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public List<Token> getTokens() throws IOException {
        return fetchToken();
    }

    public abstract List<Token> fetchToken() throws IOException;

    public char currentCharacter() throws IOException {

        return source.currentCharacter();
    }

    public char peekNextCharacter() throws IOException {

        return source.peekNextCharacter();
    }

    public char nextCharcter() throws IOException {

        return source.nextCharacter();
    }

    public char peekKthCharacter(int k) throws IOException {

        return source.peekKthCharacter(k);
    }

    public String getCurLine() {

        return source.getCurLine();
    }

    public boolean hasNextCharacter() {

        return source.hasNextCharacter();
    }

    public void close() {

        source.close();
    }

}
