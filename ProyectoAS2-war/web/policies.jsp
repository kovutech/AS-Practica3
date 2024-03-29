<%@page import="com.as.practica2.entity.PayMethod"%>
<%@page import="com.as.practica2.sbEntity.PayMethodFacade"%>
<%@page import="com.as.practica2.entity.Products"%>
<%@page import="com.as.practica2.sbEntity.ProductsFacade"%>
<%@page import="com.as.practica2.entity.Client"%>
<%@page import="com.as.practica2.sbEntity.ClientFacade"%>
<%@page import="com.as.practica2.entity.User"%>
<%@page import="com.as.practica2.entity.Policy"%>
<%@page import="com.as.practica2.sbEntity.PolicyFacade"%>
<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="com.as.practica2.singleton.LogBean"%>

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              <%-- 
Document   : clientFile
Created on : 25-feb-2018, 11:22:57
Author     : Jorge
--%>
<%@page import="com.as.practica2.stateless.ClientLevel"%>
<%@page import="com.as.practica2.stateless.CalculateDniLetter"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@page import="javax.naming.NamingException"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("user") == null) {
        RequestDispatcher r = request.getRequestDispatcher("index.jsp");
        r.forward(request, response);
    }

    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("policies.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("policies.jsp");

%>

<jsp:include page="headerA.jsp"/>

<FORM action='FrontController' method='post'>
    <TABLE border=1 class='center'>
        <TR><TD colspan='7'>AÑADIR PÓLIZA</TD></TR>
        <TR><TH><B>Identificación</B></TH><TH><B>Tipo</B></TH><TH><B>Desde</B></TH><TH><B>Hasta</B></TH><TH><B>Forma de pago</B></TH><TH colspan='2'><B>Importe</B></TH></TR>
        <TR>
        <INPUT type='hidden' name='command' value='Policies'>
        <INPUT type='hidden' name='addPolicy' value='1'>
        <TD><INPUT type='text' name='identification' value='' placeholder='Identificación' required="true" maxlength='15'></TD>
        <TD>
            <select name="type" required="true">
                <%  
                    ProductsFacade productsFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ProductsFacade");
                    List<Products> products = productsFacade.findAll();
                    for (Products elem : products) {
                %>
                <option value="<%= elem.getIdProduct()%>"><%= elem.getName()%></option>");
                <%
                    }
                %>
            </select>
        </TD>
        <TD><INPUT type='date' name='from' value='' placeholder='Desde' required="true"></TD>
        <TD><INPUT type='date' name='to' value='' placeholder='Hasta' required="true"></TD>
        <TD>
            <select name="payRange" required="true">
                <%
                    PayMethodFacade payMethodFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PayMethodFacade");
                    List<PayMethod> payMethods = payMethodFacade.findAll();
                    for (PayMethod elem : payMethods) {
                %>
                <option value="<%= elem.getIdpayMethod()%>"><%= elem.getName()%></option>");
                <%
                    }
                %>
            </select>
        </TD>
        <TD><INPUT type='number' name='price' value='' placeholder='Importe' step='any' min='0' required="true"></TD>
        <TD><INPUT type='submit' value='Añadir' class='buttonTable'></TD>
        </TR>
    </TABLE>
</FORM>
<br>
<br>
<%
    ClientLevel clientLevel = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientLevel");
    CalculateDniLetter calculateDniLetter = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/CalculateDniLetter");
    PolicyFacade policyFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PolicyFacade");
    User user = (User) session.getAttribute("user");
    Client client = (Client) session.getAttribute("client");
    List<Policy> policies = policyFacade.findByCodClient(client);
    out.print("<h2>Cliente: " + client.getName() + " " + client.getSurName() + " - Identificador: " + client.getIdentification() + calculateDniLetter.getDniLetter(client.getIdentification(), user.getName()) + " - Nivel de cliente: " + clientLevel.getClientLevel(policies.size(), user.getName()) + "</h2>");
%>
<TABLE border=1 class='center'>
    <TR><TD colspan='8'>LISTADO DE PÓLIZAS</TD></TR>
    <TR><TD>Nº póliza</TD><TD>Tipo</TD><TD>F. efecto</TD><TD>F. vencimiento</TD><TD>Forma de pago</TD><TD>Importe</TD><TD>Recibos</TD><TD>Eliminar</TD></TR>
            <%
                for (Policy elem : policies) {
                    if (elem.getCodClient().getIdClient() == client.getIdClient()) {
            %>
    <TR>
        <TD><%= elem.getIdentification()%></TD>
        <TD><%= elem.getCodProduct().getName()%></TD>
        <TD><%= elem.getFromDate()%></TD>
        <TD><%= elem.getToDate()%></TD>
        <TD><%= elem.getCodpayMethod().getName()%></TD>
        <TD><%= elem.getAmount() + " €"%></TD>

    <FORM action='FrontController'>
        <INPUT type='hidden' name='command' value='Receipts'>
        <INPUT type='hidden' name='idPolicy' value="<%= elem.getIdentification()%>">
        <INPUT type='hidden' name='newReceipt' value='1'>
        <TD><INPUT type='submit' value='Recibos' class='buttonTable'></TD>
    </FORM>


    <FORM action='FrontController' method='post'>
        <INPUT type='hidden' name='command' value='Policies'>
        <INPUT type='hidden' name='deletePolicy' value='<%= elem.getIdentification()%>'>
        <TD><INPUT type='submit' value='Eliminar' class='buttonTable'></TD>
    </FORM>
</TR>
<%}
    }%>
</TABLE>
<BR>

<FORM action='FrontController'>
    <INPUT type='hidden' name='command' value='SellRecommendation'>
    <INPUT type='submit' value='Recomendacion de venta' class='button'>
</FORM>    
<br>
<FORM action='FrontController'>
    <INPUT type='hidden' name='command' value='Main'>
    <INPUT type='submit' value='Volver' class='button'>
</FORM>

<jsp:include page="footer.jsp"/>
