<%-- 
    Document   : receipts
    Created on : 19-jun-2018, 14:12:10
    Author     : Jorge
--%>

<%@page import="com.as.practica2.entity.Receipt"%>
<%@page import="com.as.practica2.sbEntity.ReceiptFacade"%>
<%@page import="com.as.practica2.entity.Policy"%>
<%@page import="com.as.practica2.entity.Client"%>
<%@page import="com.as.practica2.entity.User"%>
<%@page import="com.as.practica2.sbEntity.PolicyFacade"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.as.practica2.stateful.PolicyBean"%>
<%@page import="com.as.practica2.stateless.CalculateDniLetter"%>
<%@page import="com.as.practica2.stateless.ClientLevel"%>
<%@page import="com.as.practica2.stateful.ReceiptBean"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.as.practica2.singleton.LogBean"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("user") == null) {
        RequestDispatcher r = request.getRequestDispatcher("index.jsp");
        r.forward(request, response);
    }

    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("receipts.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("receipts.jsp");
%>

<jsp:include page="headerA.jsp"/>

<FORM action='FrontController' method='post'>
    <TABLE border=1 class='center'>
        <TR><TD colspan='7'>AÑADIR RECIBO</TD></TR>
        <TR><TH><B>Referencia</B></TH><TH><B>F. efecto</B></TH><TH><B>Importe</B></TH><TH><B>Estado</B></TH></TR>
        <TR>
        <INPUT type='hidden' name='command' value='Receipts'>
        <INPUT type='hidden' name='addReceipt' value='1'>
        <TD><INPUT type='text' name='identification' value='' placeholder='reference' ></TD>
        <TD><INPUT type='date' name='date' value='' placeholder='F. efecto' ></TD>
        <TD><INPUT type='number' name='amount' value='' placeholder='Importe' step='any' min='0'></TD>
        <TD> <select name="paid">
                <option value="Cobrado">Cobrado</option>
                <option value="Pendiente">Pendiente</option>
            </select> </TD>                
        <TD><INPUT type='submit' value='Añadir' class='botonTable'></TD>
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
    Policy policy = (Policy) session.getAttribute("policy");
    List<Policy> policies = policyFacade.findByCodClient(client);
    out.print("<h2>Cliente: " + client.getName() + " " + client.getSurName() + " - Identificador: " + client.getIdentification() + calculateDniLetter.getDniLetter(client.getIdentification(), user.getName()) + " - Nivel de cliente: " + clientLevel.getClientLevel(policies.size(), user.getName()) + "</h2>");
    out.print("<h2>Listado de recibos de la póliza: " + policy.getIdentification() + "<h2>");
%>

<TABLE border=1 class='center'>
    <TR><TD colspan='7'>LISTADO DE RECIBOS</TD></TR>
    <TR><TD>Referencia</TD><TD>F. efecto</TD><TD>Importe</TD><TD>Estado</TD><TD>Cobrar</TD></TR>
            <%
                ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
                List<Receipt> receipts = receiptFacade.ReceiptByCodPolicy(policy);
                int count = 0;
                for (Receipt elem : receipts) {
            %>
    <TR>
        <TD><%= elem.getReference()%></TD>
        <TD><%= elem.getChargeDate()%></TD>
        <TD><%= elem.getAmount()%></TD>
            <%
                if (elem.getCodState().getName().equals("Cobrado")) {
            %>
        <TD>Cobrado</TD>
        <TD></TD>
            <%
            } else {
            %>
        <TD>Pendiente</TD>
        <TD>
            <FORM action='FrontController' method="post">
                <INPUT type='hidden' name='command' value='Receipts'>
                <INPUT type='hidden' name='charged' value="<%= elem.getIdReceipt()%>">
                <INPUT type='submit' value='Cobrado' class='botonTable'>
            </FORM>
        </TD>
        <%
            }
            count += 1;
        %>
        <TD>
            <FORM action='FrontController' method='post'>
                <INPUT type='hidden' name='command' value='Receipts'>
                <INPUT type='hidden' name='deleteReceipt' value='<%= elem.getIdReceipt()%>'>
                <INPUT type='submit' value='Eliminar' class='botonTable'>
            </FORM>
        </TD>
    </TR>
    <%
        }
    %>
</TABLE>
<BR>
<FORM action='FrontController'>
    <INPUT type='hidden' name='command' value='Policies'>
    <INPUT type='submit' value='Volver' class='boton'>
</FORM>
<jsp:include page="footer.jsp"/>
