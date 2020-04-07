package org.tinywebserver.servlet;

import java.io.PrintWriter;
import java.util.HashMap;

public interface TinyServletResponse {
  public void setContentType(String contentType);

  public void setContentLength(int length);

  public void sendRedirect(String url);

  public String getRedirect();

  public PrintWriter getOutputWriter();

  public HashMap<String, String> getResponseCookies();
}
