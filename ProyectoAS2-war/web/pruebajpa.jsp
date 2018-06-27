<%-- 
    Document   : pruebajpa
    Created on : 27-jun-2018, 17:40:42
    Author     : Jorge
--%>

<%@page import="com.as.practica2.entity.Client"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.as.practica2.SBentity.ClientFacade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ClientFacade customerFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientFacade");
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
    <%
        List<Client> customers = customerFacade.findAll();
        String idBorrar = "";
        for (Client c : customers) {
    %>
    <p><%= c.getName() + " " + c.getSurName() + " :::: Teléfono: " + c.getTelephone()%></p>
    <%  idBorrar = String.valueOf(c.getIdClient()); %>
    <%
        }

        if (idBorrar != null) {
            Client clientToDelete = customerFacade.find(Integer.parseInt(idBorrar));
            customerFacade.remove(clientToDelete);
        }
        customers = customerFacade.findAll();
        for (Client c : customers) {
    %>
    <p><%= c.getName() + " " + c.getSurName() + " :::: Teléfono: " + c.getTelephone()%></p>
    <%
        }
    %>
</body>