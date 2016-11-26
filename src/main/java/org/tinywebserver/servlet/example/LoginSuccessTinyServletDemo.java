package org.tinywebserver.servlet.example;

import org.tinywebserver.servlet.TinyServlet;
import org.tinywebserver.servlet.TinyServletRequest;
import org.tinywebserver.servlet.TinyServletResponse;
import org.tinywebserver.session.TinyHttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by user on 10/18/2016.
 */
public class LoginSuccessTinyServletDemo extends TinyServlet {

    public void doRequest(TinyServletRequest request, TinyServletResponse response) throws IOException {

        TinyHttpSession session = request.getSession();

        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            response.sendRedirect("/login.html");
            return;
        }
        PrintWriter out = response.getOutputWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("welcome to tinyservlet: " + username);
        out.println("<p> server time is now: ");
        out.println(new Date());
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
