package org.tinywebserver.controllers;

import org.apache.log4j.Logger;
import org.tinywebserver.client.TinyServletConfig;
import org.tinywebserver.client.TinyWebServerClientHandler;
import org.tinywebserver.util.TinyWebServletSessionSentinel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by user on 10/16/2016.
 */
public class TinyWebServer {

    private final static Logger log = Logger.getLogger(TinyWebServer.class);

    static {
        try {
            TinyServletConfig.getTinyServletConfigInstance();
        } catch (IOException e) {
            log.info("Exception at server startup time");
        }
    }

    public static void main(String... args) throws IOException {

        Integer serverPort = Integer.valueOf(TinyServletConfig.getProperty("tinywebserver.port"));
        ServerSocket serverSocket = new ServerSocket(serverPort);
        new TinyWebServletSessionSentinel().start();
        while (true) {
            Socket clientScoket = serverSocket.accept();
            new Thread(new TinyWebServerClientHandler(clientScoket)).start();

        }
    }

}
