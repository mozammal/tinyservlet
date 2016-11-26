<%@ page import="java.util.*, java.io.*, java.net.*" %>

<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
</head>
<BODY><%@ page import="java.util.*, java.io.*, java.net.*" %>

<img src="../img_avatar.png">
<%! int data=50; %><%
    Date date = new java.util.Date();

    String[][] name = {{"mozammal", "Hossain"}, {"Karim", "Rahim"}};
%>
<%!
    public int sqr(int n){
        return n*n;
    }
%>

<table>

    <% for (int i = 0; i < name.length; i += 1) { %>
    <tr>
        <td><%=name[i][0]%>
        </td>
        <td><%=name[i][1]%>
        </td>
    </tr>
    <% } %>
    <%= "Square of 3 is:"+sqr(3) %>
</table>
<P>The time is now <%= date %>
</BODY>
</html>