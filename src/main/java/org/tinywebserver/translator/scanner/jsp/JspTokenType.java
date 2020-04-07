package org.tinywebserver.translator.scanner.jsp;

import org.tinywebserver.translator.scanner.TokenType;

public enum JspTokenType implements TokenType {
  SCRIPTLET,
  EXPRESSION,
  DECLARATION,
  HTML,
  IMPORT
}
