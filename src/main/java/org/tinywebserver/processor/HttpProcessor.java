package org.tinywebserver.processor;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.controllers.TinyWebServer;
import org.tinywebserver.file.handler.TinyWebServerFileHandler;
import org.tinywebserver.servlet.HttpTinyServletRequest;
import org.tinywebserver.servlet.HttpTinyServletResponse;
import org.tinywebserver.servlet.TinyServlet;
import org.tinywebserver.servlet.handler.TinyServletHandler;

import java.io.*;
import java.net.Socket;

/**
 * Created by user on 10/16/2016.
 */
public class HttpProcessor extends BaseHttprocessor implements Runnable {

    private final static Logger log = Logger.getLogger(TinyWebServer.class);

    public HttpProcessor(Socket socket) {
        super(socket);
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
            TinyServlet matchingTinyServlet = TinyServletConfig.getTinyServletConfigInstance().getMatchingTinyServlet(httpTinyServletRequest.getPath());

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






