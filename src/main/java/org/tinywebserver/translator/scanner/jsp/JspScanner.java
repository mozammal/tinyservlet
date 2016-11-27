package org.tinywebserver.translator.scanner.jsp;

import org.tinywebserver.translator.scanner.Scanner;
import org.tinywebserver.translator.scanner.Source;
import org.tinywebserver.translator.scanner.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JspScanner extends Scanner {

    public static final char PERCENT = '%';
    public static final char GREATER_THAN = '>';
    public static final char NEW_LINE = '\n';
    public static final char LESS_THAN = '<';
    public static final char EQUAL = '=';
    public static final char EXCLAMATION = '!';
    public static final char AT = '@';
    public static final String IMPORT = "import";
    public static final char QUOTE = '"';
    public static final int LOOK_UP_AHEAD = 2;

    public JspScanner(Source source) {
        super(source);
    }

    public List<Token> fetchToken() throws IOException {

        skipWhiteSpace();
        List<Token> tokenList = new ArrayList<Token>();
        while (hasNextCharacter()) {
            Token token = null;
            char curCharacter = currentCharacter();
            char nextCharacter = curCharacter;
            char charAfterNext = peekKthCharacter(LOOK_UP_AHEAD);
            if (curCharacter == LESS_THAN && hasNextCharacter()) {
                nextCharacter = peekNextCharacter();

                if (nextCharacter == PERCENT && charAfterNext == EQUAL)
                    token = JspExpressionToken();
                else if (nextCharacter == PERCENT && charAfterNext == EXCLAMATION)
                    token = JspDeclarationToken();
                else if (nextCharacter == PERCENT && charAfterNext == AT)
                    token = JspImportToken();
                else if (nextCharacter == PERCENT)
                    token = JspScriptletToken();
                else
                    token = JspHtmlToken();
            } else
                token = JspHtmlToken();
            if (token != null)
                tokenList.add(token);
        }
        close();
        return tokenList;
    }

    private Token JspImportToken() throws IOException {
        StringBuffer sb = new StringBuffer();
        nextCharcter();
        nextCharcter();
        char nexCharacter = nextCharcter();
        Token token = null;
        while (!(nexCharacter == PERCENT && peekNextCharacter() == GREATER_THAN) && hasNextCharacter()) {

            String keyWord = "";
            while (Character.isLowerCase(nexCharacter) && hasNextCharacter()) {
                keyWord += nexCharacter;
                nexCharacter = nextCharcter();
            }
            if (keyWord.equals(IMPORT)) {
                nexCharacter = consumeEqualSymbol(nexCharacter);
                token = consumeImportToken(nexCharacter);
            }
            nexCharacter = nextCharcter();
        }
        consumeJspTag(nexCharacter);
        return token;
    }

    private char consumeEqualSymbol(char nexCharacter) throws IOException {
        while (nexCharacter != EQUAL && hasNextCharacter())
            nexCharacter = nextCharcter();
        if (hasNextCharacter() && nexCharacter == EQUAL)
            nextCharcter();
        return currentCharacter();
    }

    private Token consumeImportToken(char nexCharacter) throws IOException {

        StringBuffer sb = new StringBuffer();
        while (nexCharacter != QUOTE && hasNextCharacter())
            nexCharacter = nextCharcter();

        if (hasNextCharacter())
            nexCharacter = nextCharcter();

        while (nexCharacter != QUOTE && hasNextCharacter()) {

            if (nexCharacter != NEW_LINE)
                sb.append(nexCharacter);
            nexCharacter = nextCharcter();
        }
        return new Token(sb.toString(), JspTokenType.IMPORT);
    }

    private Token JspHtmlToken() throws IOException {
        StringBuffer sb = new StringBuffer();
        char nexCharacter = currentCharacter();
        while (!(nexCharacter == LESS_THAN && peekNextCharacter() == PERCENT) && hasNextCharacter()) {
            if (nexCharacter != NEW_LINE)
                sb.append(nexCharacter);
            nexCharacter = nextCharcter();
        }
        return new Token(sb.toString(), JspTokenType.HTML);
    }

    private Token JspDeclarationToken() throws IOException {
        StringBuffer sb = new StringBuffer();
        nextCharcter();
        nextCharcter();
        char nexCharacter = nextCharcter();
        nexCharacter = fetchToken(sb, nexCharacter);
        consumeJspTag(nexCharacter);
        return new Token(sb.toString(), JspTokenType.DECLARATION);
    }

    private char fetchToken(StringBuffer sb, char nexCharacter) throws IOException {
        while (!(nexCharacter == PERCENT && peekNextCharacter() == GREATER_THAN) && hasNextCharacter()) {
            if (nexCharacter != NEW_LINE)
                sb.append(nexCharacter);
            nexCharacter = nextCharcter();
        }
        return nexCharacter;
    }

    private Token JspExpressionToken() throws IOException {

        StringBuffer sb = new StringBuffer();
        nextCharcter();
        nextCharcter();
        char nexCharacter = nextCharcter();
        nexCharacter = fetchToken(sb, nexCharacter);
        consumeJspTag(nexCharacter);
        return new Token(sb.toString(), JspTokenType.EXPRESSION);
    }

    private Token JspScriptletToken() throws IOException {

        StringBuffer sb = new StringBuffer();
        nextCharcter();
        char nexCharacter = nextCharcter();
        nexCharacter = fetchToken(sb, nexCharacter);
        consumeJspTag(nexCharacter);
        return new Token(sb.toString(), JspTokenType.SCRIPTLET);
    }

    private void consumeJspTag(char nexCharacter) throws IOException {
        if (nexCharacter == PERCENT && peekNextCharacter() == GREATER_THAN) {
            nextCharcter();
            nextCharcter();
        }
    }

    private void skipWhiteSpace() throws IOException {

        char curCharacter = currentCharacter();
        while (Character.isWhitespace(curCharacter)) {
            curCharacter = nextCharcter();
        }
    }
}
