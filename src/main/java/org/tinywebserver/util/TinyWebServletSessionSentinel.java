package org.tinywebserver.util;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.servlet.HttpTinyServletRequest;
import org.tinywebserver.session.TinyHttpSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 10/19/2016.
 */
public class TinyWebServletSessionSentinel extends Thread {

    private final static Logger log = Logger.getLogger(TinyWebServletSessionSentinel.class);
    public static final String TINYWEBSERVER_SESSION_TIMEOUT = "tinywebserver.session.timeout";

    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(180000L);
            } catch (InterruptedException ex) {
            }
            log.info("garbage collector for session is running....");
            long currentTime = System.currentTimeMillis();
            String sessionTimeoutString = TinyServletConfig.getProperty(TINYWEBSERVER_SESSION_TIMEOUT);
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
