package org.tinywebserver.servlet;

import org.tinywebserver.session.TinyHttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 10/17/2016.
 */
public interface TinyServletRequest {
    ConcurrentHashMap<String, TinyHttpSession> sessionManager = new ConcurrentHashMap<String, TinyHttpSession>();

    public String getContentType();

    public int getContentLength();

    Map<String, String> getCookies() throws IOException;

    TinyHttpSession getSession();

    Map<String, String> getHeaders();

    String getMethod();

    String getPath();

    String getQueryString();

    float getVersion();

    String getHeader(String name);

    Set<String> getHeaderNames();

    HashMap<String, String> getResponseCookies();

    HashMap<String, String> getParameters();

    Map<String, String> getRequestCookies();
}
