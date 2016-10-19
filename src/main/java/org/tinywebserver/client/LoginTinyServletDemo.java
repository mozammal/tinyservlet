package org.tinywebserver.client;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by user on 10/18/2016.
 */
public class LoginTinyServletDemo extends TinyServlet {

    public void doRequest(TinyServletRequest request, TinyServletResponse response) {

        TinyHttpSession session = request.getSession();
        if (session.getAttribute("username") != null && session.getAttribute("password") != null) {
            response.sendRedirect("/home");
            return;
        }


        String name = request.getParameters().get("username");
        String password = request.getParameters().get("password");

        if (name == null || password == null) {
            response.sendRedirect("/login.html");
            return;
        }


        session.setAttribute("username", name);
        session.setAttribute("password", password);
        response.sendRedirect("/home");
    }
}
