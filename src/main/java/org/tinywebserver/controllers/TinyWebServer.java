package org.tinywebserver.controllers;

import org.apache.log4j.Logger;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.processor.HttpProcessor;
import org.tinywebserver.translator.JspToServletProcessor;
import org.tinywebserver.util.TinyWebServletSessionSentinel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TinyWebServer {

  private static final int NTHREADS = 50;
  private static final Logger LOG = Logger.getLogger(TinyWebServer.class);
  private static final Executor workers = Executors.newFixedThreadPool(NTHREADS);

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

    Integer serverPort =
        Integer.valueOf(
            TinyServletConfig.getTinyServletConfigInstance().getProperty("tinywebserver.port"));
    ServerSocket serverSocket = new ServerSocket(serverPort);
    new TinyWebServletSessionSentinel().start();
    while (true) {
      final Socket clientSocket = serverSocket.accept();
      workers.execute(new HttpProcessor(clientSocket));
    }
  }
}
