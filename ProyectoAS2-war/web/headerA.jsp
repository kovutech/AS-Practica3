<%-- 
    Document   : header
    Created on : 04-jun-2018, 18:59:48
    Author     : Jorge
--%>

<%@page import="com.as.practica2.entity.User"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Seguros lorem ipsun s.a.</title>
</head>
<body>
    <div class="header">
        <div class="cajaHeader">
            <div class="izquierda">
                <img src="./img/logo.png" alt="" height="150px" width="150px">
            </div>
            <div class="derecha">
                <h1 class="title">Seguros lorem ipsun s.a.</h1>
            </div>
            <div style="clear:both"></div>
        </div>
        <div class="cajaUsuario">
            
            <div class="logoutIzquierda">
                <%
                    User user = (User) session.getAttribute("user");
                    out.print("<h3 class='textUser'>Agente: " + user.getName() + "</h3>");
                %>
            </div>
            <div class="logoutDerecha">
                <FORM action='FrontController'>
                    <INPUT type='hidden' name='command' value='Logout'>
                    <INPUT type='submit' value='Cerrar sesión' class='logoutButton'>
                </FORM>
            </div>
            <div style="clear:both"></div>
        </div>
    </div>
</body>
