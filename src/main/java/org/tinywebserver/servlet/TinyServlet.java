package org.tinywebserver.servlet;

import java.io.IOException;

public abstract class TinyServlet {
  public abstract void doRequest(TinyServletRequest request, TinyServletResponse response)
      throws IOException;
}
