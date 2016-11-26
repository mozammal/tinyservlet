package org.tinywebserver.servlet.example;

import org.tinywebserver.servlet.TinyServlet;
import org.tinywebserver.servlet.TinyServletRequest;
import org.tinywebserver.servlet.TinyServletResponse;
import org.tinywebserver.session.TinyHttpSession;

import java.io.IOException;


public class LoginTinyServletDemo extends TinyServlet {

    public void doRequest(TinyServletRequest request, TinyServletResponse response) throws IOException {

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
