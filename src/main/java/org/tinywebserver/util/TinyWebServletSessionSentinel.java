package org.tinywebserver.util;

import org.apache.log4j.Logger;
import org.tinywebserver.client.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 10/19/2016.
 */
public class TinyWebServletSessionSentinel extends Thread {

    private final static Logger log = Logger.getLogger(TinyWebServletSessionSentinel.class);

    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(180000L);
            } catch (InterruptedException ex) {
            }
            log.info("garbage collector for session is running....");
            long currentTime = System.currentTimeMillis();
            String sessionTimeoutString = TinyServletConfig.getProperty("tinywebserver.session.timeout");
            int sessionTimeOut = Integer.parseInt(sessionTimeoutString);

            ConcurrentHashMap<String, TinyHttpSession> sessionManager = HttpTinyServletRequest.getSessionManager();
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
