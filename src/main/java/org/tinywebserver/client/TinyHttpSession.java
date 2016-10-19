package org.tinywebserver.client;

import java.util.HashMap;

/**
 * Created by user on 10/17/2016.
 */
public class TinyHttpSession {

    private HashMap<String, Object> sessions = new HashMap<String, Object>();

    private long lastAccessed;

    public void setAttribute(String key, Object value) {
        sessions.put(key, value);
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public Object getAttribute(String key) {
        return sessions.get(key);
    }

    public long getLastAccessed() {
        return lastAccessed;
    }
}
