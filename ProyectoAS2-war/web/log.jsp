<%-- 
    Document   : stadistics
    Created on : 21-jun-2018, 17:12:10
    Author     : Jorge
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.as.practica2.singleton.LogBean"%>
<%
    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("log.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("log.jsp");
%>

<jsp:include page="headerB.jsp"/>

<body>
    <h1>Traza del log</h1>
    <table border="1" class="stadistics">
        <% List<String> traza = logBean.getTraza();
            for (String elem : traza) {
        %>
        <tr>
            <td><% out.println(elem); %></td>
        </tr>
        <%
            }
        %>
    </table>
    <br>
    <br>
    <FORM action='FrontController'>
        <INPUT type='hidden' name='command' value='Main'>
        <INPUT type='submit' value='Volver' class='boton'>
    </FORM>
    <jsp:include page="footer.jsp"/>
</body>

