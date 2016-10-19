package org.tinywebserver.client;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by user on 10/17/2016.
 */
public abstract class TinyServlet {
    public abstract void doRequest(TinyServletRequest request, TinyServletResponse response);
}
