package org.tinywebserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyHttpSession {

  private Map<String, Object> sessions = new HashMap<>();

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
