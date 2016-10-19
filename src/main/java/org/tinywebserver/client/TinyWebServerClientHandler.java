package org.tinywebserver.client;

import org.apache.log4j.Logger;
import org.tinywebserver.controllers.TinyWebServer;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by user on 10/16/2016.
 */
public class TinyWebServerClientHandler extends BaseClientProcessor implements Runnable {

    private final static Logger log = Logger.getLogger(TinyWebServer.class);

    public TinyWebServerClientHandler(Socket socket) {
        super(socket);
    }

    public void parseRequest() {

    }

    public void prepareResponse() {

    }

    public void run() {

        try {
            InputStream inputStream = null;

            inputStream = socket.getInputStream();

            OutputStream outputStream = socket.getOutputStream();

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));

            HttpTinyServletRequest httpTinyServletRequest = new HttpTinyServletRequest(inputStream);
            httpTinyServletRequest.readRequest();
            HttpTinyServletResponse httpTinyServletResponse = new HttpTinyServletResponse(printWriter);
            TinyServlet matchingTinyServlet = TinyServletConfig.getMatchingTinyServlet(httpTinyServletRequest.getPath());

            if (matchingTinyServlet != null) {
                TinyServletHandler tinyServletHandler = new TinyServletHandler();
                tinyServletHandler.processTinyServlet(matchingTinyServlet, httpTinyServletRequest, httpTinyServletResponse);
            } else {
                TinyWebServerFileHandler tinyWebServerFileHandler = new TinyWebServerFileHandler();
                tinyWebServerFileHandler.processFile(httpTinyServletRequest, outputStream);
            }

            inputStream.close();
            socket.close();
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }
}






