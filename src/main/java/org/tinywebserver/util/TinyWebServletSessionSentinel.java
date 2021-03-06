package org.tinywebserver.util;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.servlet.HttpTinyServletRequest;
import org.tinywebserver.session.TinyHttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyWebServletSessionSentinel extends Thread {

  private static final Logger log = Logger.getLogger(TinyWebServletSessionSentinel.class);

  public static final String TINYWEBSERVER_SESSION_TIMEOUT = "tinywebserver.session.timeout";

  public void run() {

    while (true) {
      try {
        Thread.sleep(180000L);
      } catch (InterruptedException ex) {
      }
      log.info("garbage collector is running....");
      long currentTime = System.currentTimeMillis();
      String sessionTimeoutString = null;
      try {
        sessionTimeoutString =
            TinyServletConfig.getTinyServletConfigInstance()
                .getProperty(TINYWEBSERVER_SESSION_TIMEOUT);
      } catch (IOException e) {
        log.debug(e.getCause());
      }
      int sessionTimeOut = Integer.parseInt(sessionTimeoutString);

      ConcurrentHashMap<String, TinyHttpSession> sessionManager =
          HttpTinyServletRequest.getSessionManager();
      for (Map.Entry<String, TinyHttpSession> sessionEntity : sessionManager.entrySet()) {
        String sessionKey = sessionEntity.getKey();
        TinyHttpSession sessionValue = sessionEntity.getValue();
        if (currentTime - sessionValue.getLastAccessed() > sessionTimeOut) {
          sessionManager.remove(sessionKey);
          log.info("session deleted for key: " + sessionKey);
        }
      }
    }
  }
}
