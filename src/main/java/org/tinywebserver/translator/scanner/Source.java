package org.tinywebserver.translator.scanner;

import java.io.BufferedReader;
import java.io.IOException;

public class Source {
  private BufferedReader source;

  private String curLine;

  private int curPosition;

  public Source(BufferedReader source) {
    this.source = source;
    curPosition = -2;
  }

  public char currentCharacter() throws IOException {

    if (curPosition == -2) {
      readNewLine();
      return nextCharacter();
    } else if (curLine == null) return ' ';
    else if (curPosition == -1 || (curPosition == curLine.length())) return '\n';
    else if (curPosition > curLine.length()) {
      readNewLine();
      return nextCharacter();
    } else return curLine.charAt(curPosition);
  }

  public char nextCharacter() throws IOException {

    curPosition++;
    return currentCharacter();
  }

  private void readNewLine() throws IOException {

    curPosition = -1;
    curLine = source.readLine();
  }

  public char peekNextCharacter() throws IOException {

    currentCharacter();
    if (curLine == null) return ' ';
    int nextPosition = curPosition + 1;
    return nextPosition < curLine.length() ? curLine.charAt(nextPosition) : '\n';
  }

  public char peekKthCharacter(int k) throws IOException {

    currentCharacter();
    if (curLine == null) return ' ';
    int nextPosition = curPosition + k;
    return nextPosition < curLine.length() ? curLine.charAt(nextPosition) : '\n';
  }

  public String getCurLine() {

    return curLine;
  }

  public boolean hasNextCharacter() {

    return curLine != null;
  }

  public void close() {

    if (source != null) {
      try {
        source.close();
      } catch (IOException ex) {

      }
    }
  }
}
