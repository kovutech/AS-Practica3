<%-- 
    Document   : SellRecomendation
    Created on : 26-feb-2018, 18:13:45
    Author     : Jorge
--%>

<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="com.as.practica2.singleton.LogBean"%>
<%@page import="com.as.practica2.stateful.PolicyBean"%>
<%@page import="com.as.practica2.stateless.SellRecommendation"%>
<%@page import="com.as.practica2.stateless.CalculateDniLetter"%>
<%@page import="com.as.practica2.stateless.ClientLevel"%>
<%@page import="com.as.practica2.object.Policy"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("user") == null) {
        RequestDispatcher r = request.getRequestDispatcher("index.jsp");
        r.forward(request, response);
    }

    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("sellRecommendation.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("sellRecommendation.jsp");
%>
<jsp:include page="headerA.jsp"/>
<%
    ClientLevel clientLevel = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientLevel");
    SellRecommendation sellRecomendation = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/SellRecommendation");
    CalculateDniLetter calculateDniLetter = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/CalculateDniLetter");

    List<String> clientData = new ArrayList<String>();
    clientData = (ArrayList) session.getAttribute("clientData");
    PolicyBean aux = (PolicyBean) session.getAttribute("policyList");
    List<Policy> policies = aux.getPolicyList(clientData.get(2), (String) session.getAttribute("user"));

    out.print("<h2>Cliente: " + clientData.get(0) + " " + clientData.get(1) + " - Identificador: " + clientData.get(2) + calculateDniLetter.getDniLetter(clientData.get(2), (String) session.getAttribute("user")) + " - Nivel de cliente: " + clientLevel.getClientLevel(policies.size(), (String) session.getAttribute("user")) + "</h2>");
    List<String> stringPolicies = new ArrayList<String>();

    for (Policy elem : policies) {
        stringPolicies.add(elem.getType());
    }

    List<String> auxTypesInsurances = sellRecomendation.getRecommendation(stringPolicies, (String) session.getAttribute("user"));

    out.print("<div class='textAux'>Al cliente se le deberían de ofrecer los siguientes productos.<div><BR>");

    out.print("<TABLE class='insurance'>");
    for (String elem : auxTypesInsurances) {
        out.print("<TR>");
        out.print("<TD>" + elem + "</TD>");
        out.print("<TD><img src='./img/" + elem + ".png' height='75' width='75'></TD>");
        out.print("</TR>");
    }
    out.print("</TABLE>");

%>
<br>
<br>
<FORM action='FrontController'>
    <INPUT type='hidden' name='command' value='Policies'>
    <INPUT type='submit' value='Volver' class='boton'>
</FORM>
<jsp:include page="footer.jsp"/>


