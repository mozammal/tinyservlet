package org.tinywebserver.servlet.translated; 
import java.io.*;
import java.util.*;
import org.tinywebserver.servlet.*;
import org.tinywebserver.session.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.net.*;
public class Test extends TinyServlet { 

static  int data=50; 
static     public int sqr(int n){        return n*n;    }
 public void doRequest(TinyServletRequest request, TinyServletResponse response) throws IOException{ 
 PrintWriter out = response.getOutputWriter();
out.println("<html><head>    <style>        table, th, td {            border: 1px solid black;        }    </style></head></head><BODY>");
out.println("<img src=\"../img_avatar.png\">");
    Date date = new java.util.Date();    String[][] name = {{"mozammal", "Hossain"}, {"Karim", "Rahim"}};
out.println("");
out.println("<table>    ");
 for (int i = 0; i < name.length; i += 1) { 
out.println("    <tr>        <td>");
out.println(name[i][0]);
out.println("        </td>        <td>");
out.println(name[i][1]);
out.println("        </td>    </tr>    ");
 } 
out.println("    ");
out.println( "Square of 3 is:"+sqr(3) );
out.println("</table><P>The time is now ");
out.println( date );
out.println("</BODY></html>");
}}
