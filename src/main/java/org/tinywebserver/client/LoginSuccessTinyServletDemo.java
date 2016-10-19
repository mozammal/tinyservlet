package org.tinywebserver.client;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 10/18/2016.
 */
public class LoginSuccessTinyServletDemo extends TinyServlet {

    public void doRequest(TinyServletRequest request, TinyServletResponse response) {

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
