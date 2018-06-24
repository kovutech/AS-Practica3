<%-- 
    Document   : header
    Created on : 04-jun-2018, 18:59:48
    Author     : Jorge
--%>

<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Seguros lorem ipsun s.a.</title>
</head>
<body>
    <div class="header">
        <h1 class="title">Seguros lorem ipsun s.a.</h1>
        <FORM action='FrontController'>
            <INPUT type='hidden' name='command' value='Logout'>
            <INPUT type='submit' value='Cerrar sesión' class='botonLogout'>
        </FORM><BR>
        <%
            String user = (String) session.getAttribute("user");
            out.print("<h3>Agente: " + user + "</h3>");
        %>
    </div>
</body>
