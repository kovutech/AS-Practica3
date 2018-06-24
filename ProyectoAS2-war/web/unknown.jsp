<%-- 
    Document   : unknown
    Created on : 24-feb-2018, 13:47:51
    Author     : Jorge
--%>

<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.as.practica2.singleton.LogBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("unknow.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("unknown.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Unknown</title>
    </head>
    <body>
        <h1 class="title">Torniquete seguros s.a.</h1>
        <div class="contenedorCentrado"><h2>Comando desconocido</h2></div>
    </body>
</html>
