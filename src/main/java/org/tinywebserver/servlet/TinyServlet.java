package org.tinywebserver.servlet;

/**
 * Created by user on 10/17/2016.
 */
public abstract class TinyServlet {
    public abstract void doRequest(TinyServletRequest request, TinyServletResponse response);
}
