<%-- 
    Document   : stadistics
    Created on : 21-jun-2018, 17:12:10
    Author     : Jorge
--%>
<%@page import="java.util.Map"%>
<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.as.practica2.singleton.LogBean"%>

<%
    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("stadistics.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("stadistics.jsp");
%>
<jsp:include page="headerB.jsp"/>
<body>
    <h1>Estadisticas</h1>
    <h2>Usuarios conectados</h2>
    <table border="1" class="stadistics">
        <tr>
            <th>Usuarios</th><th><% out.println(estadisticasBean.getUsers()); %></th>
        </tr>
    </table>
    <br>
    <br>
    <h2>Componentes ejecutados</h2>
    <table border="1" class='stadistics'>
        <tr>

            <th>Componente</th><th>Nº de veces</th>
        </tr>
        <%
            Map<String, Integer> componentsCount = estadisticasBean.getComponentCount();
            for (Map.Entry<String, Integer> entry : componentsCount.entrySet()) {
        %>
        <tr>
            <td><% out.println(entry.getKey()); %></td>
            <td><% out.println(entry.getValue());%></td>
        </tr>
        <% }%>
    </table>
    <br>
    <br>
    <h2>Componentes visitados</h2>
    <table border="1" class='stadistics'>
        <tr>
            <th>Componente</th><th>Nº de veces</th>
        </tr>
        <%
            Map<String, Integer> componentsUsers = estadisticasBean.getComponentUsers();
            for (Map.Entry<String, Integer> entry : componentsUsers.entrySet()) {
        %>
        <tr>
            <td><% out.println(entry.getKey()); %></td>
            <td><% out.println(entry.getValue());%></td>
        </tr>
        <% }%>
    </table>
    <br>
    <br>
    <h2>Páginas visitadas</h2>
    <table border="1" class='stadistics'>
        <tr>

            <th>Componente</th><th>Nº de veces</th>
        </tr>
        <%
            Map<String, Integer> pagesCount = estadisticasBean.getPageCount();
            for (Map.Entry<String, Integer> entry : pagesCount.entrySet()) {
        %>
        <tr>
            <td><% out.println(entry.getKey()); %></td>
            <td><% out.println(entry.getValue());%></td>
        </tr>
        <% }%>
    </table>
    <br>
    <br>
    <FORM action='FrontController'>
        <INPUT type='hidden' name='command' value='Main'>
        <INPUT type='submit' value='Volver' class='boton'>
    </FORM>
    <jsp:include page="footer.jsp"/>
</body>

