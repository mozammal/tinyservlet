package org.tinywebserver.client;

import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 10/17/2016.
 */

public class HttpTinyServletRequest implements TinyServletRequest {
    private final static Logger log = Logger.getLogger(HttpTinyServletRequest.class);
    public static final String COOKIE = "cookie";


    private BufferedReader inputScanner;
    private String method, path, queryString;
    private float version;
    private HashMap<String, String> headers;
    private HashMap<String, String> parameters;
    Map<String, String> requestCookies;
    HashMap<String, String> responseCookies;

    public HttpTinyServletRequest(InputStream inputStream) {
        this.inputScanner = new BufferedReader(new InputStreamReader(inputStream));
        this.headers = new HashMap<String, String>();
        this.parameters = new HashMap<String, String>();
        this.requestCookies = new HashMap<String, String>();
        this.responseCookies = new HashMap<String, String>();
    }

    public void readRequest() throws IOException {
        String request = inputScanner.readLine();
        if (request == null)
            throw new IOException("Null request line");

        try {
            parseMethod(request);
            parseRequest(request);
        } catch (Exception ex) {
            log.info("Exception readRequest: "+ex);
        }

        readHeaders();

        getCookies();

        if (getMethod().equals("POST")) {
            parseBody();
        }
    }

    private void parseBody() throws IOException {

        int contentLength = getContentLength();
        StringBuffer body = new StringBuffer();
        for (int i = 0; i < contentLength; i++) {
            int ch = inputScanner.read();
            body.append((char) ch);
        }
        log.info("body " + body.toString());
        parseQueryString(body.toString());
    }


    protected void parseMethod(String requestLine)  {

        String method = requestLine.split(" ")[0];
        if (method.equals("GET"))
            this.method = "GET";
        else if (method.equals("POST"))
            this.method = "POST";
        else
            new NotImplementedException();
    }

    protected void parseRequest(String requestLine) throws  UnsupportedEncodingException {
        String[] tokens = requestLine.split(" ");
        path = tokens[1];
        queryString = null;
        int queryIndex = path.indexOf('?');
        if (queryIndex > 0) {
            queryString = path.substring(queryIndex + 1);
            path = path.substring(0, queryIndex);
        }
        path = URLDecoder.decode(path, "UTF-8");
        if (queryString != null)
            parseQueryString(queryString);
    }

    protected void readHeaders() throws IOException {
        String header;
        while (((header = inputScanner.readLine()) != null) && !header.equals("")) {
            int colonIdx = header.indexOf(':');
            if (colonIdx != -1) {
                String name = header.substring(0, colonIdx);
                String value = header.substring(colonIdx + 1);
                headers.put(name.toLowerCase(), value.trim());
                log.info(name + ": " + value);
            }
        }
    }

    public Map<String, String> getCookies() throws IOException {
        String cookieKeyValueLine = headers.get(COOKIE);
        if (cookieKeyValueLine == null)
            return new HashMap<String, String>();

        String[] cookieKeyValueList = cookieKeyValueLine.split(";");

        for (String cookie : cookieKeyValueList) {

            String[] cookieKeyValue = cookie.split("=");
            if (cookieKeyValue.length == 2) {
                String cookieName = cookieKeyValue[0].trim();
                String cookieValue = cookieKeyValue[1].trim();
                requestCookies.put(cookieName, cookieValue);
            }
        }
        return requestCookies;
    }

    public void parseQueryString(String queryString)
            throws UnsupportedEncodingException {

        String[] qTokens = queryString.split("&");
        for (String qToekn : qTokens) {
            String[] ptokens = qToekn.split("=");
            if (ptokens.length == 2) {
                String parameterName = URLDecoder.decode(ptokens[0], "utf-8");
                String parameterValue = URLDecoder.decode(ptokens[1], "utf-8");
                parameters.put(parameterName, parameterValue);
            }
        }
    }

    public TinyHttpSession getSession() {

        String sessionId = TinyServletConfig.getProperty("tinywebserver.session.cookie");
        String sessionCookie = getRequestCookie(sessionId);

        if (sessionCookie == null) {
            sessionCookie = UUID.randomUUID().toString();
            setResponseCookie(sessionId, sessionCookie);
        }
        TinyHttpSession session = sessionManager.get(sessionCookie);
        if (session == null) {
            session = new TinyHttpSession();
            sessionManager.put(sessionCookie, session);
        }
        session.setLastAccessed(System.currentTimeMillis());
        return session;
    }

    public void setResponseCookie(String cookieName, String cookieValue) {
        responseCookies.put(cookieName, cookieValue);
    }

    public String getContentType() {
        return null;
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("content-length"));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    public float getVersion() {
        return version;
    }

    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }

    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    public String getRequestCookie(String cookieName) {
        return requestCookies.get(cookieName);
    }

    public HashMap<String, String> getResponseCookies() {
        return responseCookies;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getRequestCookies() {
        return requestCookies;
    }

    public static ConcurrentHashMap<String, TinyHttpSession> getSessionManager() {
        return sessionManager;
    }
}
