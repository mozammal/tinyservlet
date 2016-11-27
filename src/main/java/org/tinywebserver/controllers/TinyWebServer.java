package org.tinywebserver.controllers;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.processor.HttpProcessor;
import org.tinywebserver.translator.JspToServletProcessor;
import org.tinywebserver.util.TinyWebServletSessionSentinel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;


public class TinyWebServer {

    private final static Logger LOG = Logger.getLogger(TinyWebServer.class);

    static {
        try {
            TinyServletConfig.getTinyServletConfigInstance().initializeServerConfigProperties();
            JspToServletProcessor.getJspToServletProcessorInstance().translateJspToServlet(true);
        } catch (IOException e) {
            LOG.info("Exception at server startup time");
        } catch (InterruptedException e) {
            LOG.info("Interrupted excetion at server startup time");
        } catch (ClassNotFoundException e) {
            LOG.info("class not found excetion at server startup time");
        } catch (URISyntaxException e) {
        }
    }

    public static void main(String... args) throws IOException {

        Integer serverPort = Integer.valueOf(TinyServletConfig.getTinyServletConfigInstance().getProperty("tinywebserver.port"));
        ServerSocket serverSocket = new ServerSocket(serverPort);
        new TinyWebServletSessionSentinel().start();
        while (true) {
            Socket clientScoket = serverSocket.accept();
            new Thread(new HttpProcessor(clientScoket)).start();

        }
    }

}
