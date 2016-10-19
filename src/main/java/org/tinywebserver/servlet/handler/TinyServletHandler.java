package org.tinywebserver.servlet.handler;

import org.tinywebserver.servlet.HttpTinyServletRequest;
import org.tinywebserver.servlet.HttpTinyServletResponse;
import org.tinywebserver.servlet.TinyServlet;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by user on 10/18/2016.
 */
public class TinyServletHandler {


    public void processTinyServlet(TinyServlet tinyServlet, HttpTinyServletRequest httpTinyServletRequest,
                                   HttpTinyServletResponse httpTinyServletResponse) {

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(byteArray);
        HttpTinyServletResponse response = new HttpTinyServletResponse(out);
        PrintWriter outputWriter = httpTinyServletResponse.getOutputWriter();
        tinyServlet.doRequest(httpTinyServletRequest, response);

        if (response.getRedirect() != null) {
            outputWriter.println("HTTP/1.1 302 Found");
            outputWriter.println("Location: " + response.getRedirect());
            addCookieHeaders(httpTinyServletRequest.getResponseCookies(), outputWriter);
            outputWriter.println();
        } else {
            outputWriter.println("HTTP/1.1 200 OK");
            if (httpTinyServletRequest.getContentType() != null)
                outputWriter.println("Content-Type: " + httpTinyServletRequest.getContentType());
            else {
                outputWriter.println("Content-Type: text/html");
                addCookieHeaders(httpTinyServletRequest.getResponseCookies(), outputWriter);
                outputWriter.println();
                out.flush();
                outputWriter.println(byteArray.toString());
                outputWriter.flush();
            }
        }
        outputWriter.close();
    }

    void addCookieHeaders(HashMap<String, String> responseCookies,
                          PrintWriter output) {
        for (String cookieName : responseCookies.keySet()) {
            output.print("Set-Cookie: ");
            output.print(cookieName);
            output.print("=");
            String cookieValue = responseCookies.get(cookieName);
            output.print(cookieValue);
            output.println("; Path=/");
        }
    }
}
