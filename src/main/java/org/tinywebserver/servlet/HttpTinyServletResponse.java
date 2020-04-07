package org.tinywebserver.servlet;

import java.io.PrintWriter;
import java.util.HashMap;

public class HttpTinyServletResponse implements TinyServletResponse {

  private PrintWriter outputWriter;
  private HashMap<String, String> responseCookies;
  private String redirect;

  public HttpTinyServletResponse(PrintWriter outputWriter) {
    this.outputWriter = outputWriter;
    this.responseCookies = new HashMap<String, String>();
  }

  public void setContentType(String contentType) {}

  public String getContentType() {
    return null;
  }

  public void setContentLength(int length) {}

  public void sendRedirect(String newUrl) {
    redirect = newUrl;
  }

  public String getRedirect() {
    return redirect;
  }

  public PrintWriter getOutputWriter() {
    return outputWriter;
  }

  public HashMap<String, String> getResponseCookies() {
    return responseCookies;
  }
}
