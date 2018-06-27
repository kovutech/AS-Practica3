<%-- 
    Document   : index
    Created on : 26-feb-2018, 14:18:53
    Author     : Jorge
--%>

<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="com.as.practica2.singleton.LogBean"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="com.as.practica2.stateful.UserBean"%>
<%@page import="com.as.practica2.object.User"%>
<%@page import="javax.naming.InitialContext"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    if (session.getAttribute("user") != null) {
        response.sendRedirect("main.jsp");
    }
/*
    if (session.getAttribute("userList") == null) {
        try {
            UserBean userBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/UserBean");
            session.setAttribute("userList", userBean);
            UserBean userList = (UserBean) session.getAttribute("userList");

            userList.addUser("jorge", new User("jorge", "1234", "kovutech@gmail.com"));

        } catch (NamingException ex) {
        }
    }
*/

    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("index.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("index.jsp");
%>
<jsp:include page="headerB.jsp"/>
<!DOCTYPE html>
<body>
    <div class="box">
        <form action="FrontController" method='post' class="contenedorCentrado">
            <h1>Agente</h1>
            <input type="text" name="user" placeholder="User" class="inputText" required/><br><br>
            <input type="password" name="pass" placeholder="Password" class="inputText" required/><br><br>
            <input type="hidden" name="command" value="Main">
            <input type="hidden" name="login" value="1">
            <input type="submit" value="Login" class="boton"/>
        </form>
        <br>
        <form action="FrontController" method='post'>
            <input type="hidden" name="command" value="Register">
            <input type="submit" name="Registro" value="Registro" class='boton'>
        </form>
    </div>
</body>
<jsp:include page="footer.jsp"/>