package org.tinywebserver.file.handler;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.servlet.HttpTinyServletRequest;
import org.tinywebserver.util.TinyWebServerUtility;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TinyWebServerFileHandler {

  private static final Logger log = Logger.getLogger(TinyWebServerFileHandler.class);

  public void processFile(HttpTinyServletRequest httpTinyServletRequest, OutputStream outputStream)
      throws IOException, URISyntaxException {

    PrintWriter outputWriter = new PrintWriter(new OutputStreamWriter(outputStream));
    String baseDirectory =
        System.getProperty("user.dir")
            + TinyServletConfig.getTinyServletConfigInstance()
                .getProperty("tinywebserver.root.doc.directory");

    log.info("base path " + baseDirectory + " " + System.getProperty("user.dir"));

    File dir = new File(baseDirectory);
    File file = new File(dir, httpTinyServletRequest.getPath());

    if (file.exists() && file.isDirectory()) {
      file = new File(file, "index.html");
    }

    if (!file.exists()) {
      outputWriter.println("HTTP/1.0 404 Not Found");
      outputWriter.println();
    } else {
      outputWriter.println("HTTP/1.0 200 OK");
      String ctype = guessContentType(file.getName());
      log.info("resource: " + file.getName());
      outputWriter.println("Content-Type: " + ctype);
      outputWriter.println();
      outputWriter.flush();
      FileInputStream in = new FileInputStream(file);
      int c;
      while ((c = in.read()) >= 0) outputStream.write(c);
      in.close();
      outputWriter.close();
    }
  }

  static String guessContentType(String name) {

    return TinyWebServerUtility.guessContentType(name.toLowerCase());
  }
}
