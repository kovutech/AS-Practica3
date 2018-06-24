<%-- 
    Document   : receipts
    Created on : 19-jun-2018, 14:12:10
    Author     : Jorge
--%>

<%@page import="com.as.practica2.object.Policy"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.as.practica2.stateful.PolicyBean"%>
<%@page import="com.as.practica2.stateless.CalculateDniLetter"%>
<%@page import="com.as.practica2.stateless.ClientLevel"%>
<%@page import="com.as.practica2.stateful.ReceiptBean"%>
<%@page import="com.as.practica2.object.Receipt"%>
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

    List<String> clientData = (List<String>) session.getAttribute("clientData");
    String currentPolicy = (String) session.getAttribute("currentPolicy");
    if (session.getAttribute("receiptList") == null) {
        try {
            ReceiptBean receiptBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptBean");
            session.setAttribute("receiptList", receiptBean);
            ReceiptBean receiptList = (ReceiptBean) session.getAttribute("receiptList");

            receiptList.addReceipt(currentPolicy, new Receipt("364758432849", "1/05/2018", "112", false), (String) session.getAttribute("user"));
            receiptList.addReceipt(currentPolicy, new Receipt("364758432868", "1/06/2018", "112", false), (String) session.getAttribute("user"));

            session.setAttribute("receiptList", receiptList);
        } catch (NamingException ex) {
        }
    }
    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("receipts.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("receipts.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Torniquete seguros s.a.</title>
    </head>
    <body>
        <jsp:include page="headerA.jsp"/>

        <FORM action='FrontController' method='post'>
            <TABLE border=1 class='center'>
                <TR><TD colspan='7'>AÑADIR RECIBO</TD></TR>
                <TR><TH><B>Referencia</B></TH><TH><B>F. efecto</B></TH><TH><B>Importe</B></TH><TH><B>Estado</B></TH></TR>
                <TR>
                <INPUT type='hidden' name='command' value='Receipts'>
                <INPUT type='hidden' name='addReceipt' value='1'>
                <TD><INPUT type='text' name='id' value='' placeholder='reference' ></TD>
                <TD><INPUT type='date' name='date' value='' placeholder='F. efecto' ></TD>
                <TD><INPUT type='number' name='amount' value='' placeholder='Importe' step='any' min='0'></TD>
                <TD> <select name="paid">
                        <option value="true">Cobrado</option>
                        <option value="false">Pendiente</option>
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

            PolicyBean aux = (PolicyBean) session.getAttribute("policyList");
            ReceiptBean receiptBean = (ReceiptBean) session.getAttribute("receiptList");

            List<Policy> policies = new ArrayList<Policy>();
            policies = aux.getPolicyList(clientData.get(2), (String) session.getAttribute("user"));
            session.setAttribute("policies", policies);

            List<Receipt> receipts = new ArrayList<Receipt>();
            receipts = receiptBean.getReceiptList(currentPolicy, (String) session.getAttribute("user"));

            session.setAttribute("receipts", receipts);

            out.print("<h2>Cliente: " + clientData.get(0) + " " + clientData.get(1) + " - Identificador: " + clientData.get(2) + calculateDniLetter.getDniLetter(clientData.get(2), (String) session.getAttribute("user")) + " - Nivel de cliente: " + clientLevel.getClientLevel(policies.size(), (String) session.getAttribute("user")) + "</h2>");

            out.print("<TABLE border=1 class='center'>");
            out.print("<TR><TD colspan='7'>LISTADO DE RECIBOS</TD></TR>");
            out.print("<TR><TD>Referencia</TD><TD>F. efecto</TD><TD>Importe</TD><TD>Estado</TD><TD>Cobrar</TD></TR>");
            int count = 0;
            for (Receipt elem : receipts) {
                out.print("<TR>");
                out.print("<TD>" + elem.getReference() + "</TD>");
                out.print("<TD>" + elem.getDate() + "</TD>");
                out.print("<TD>" + elem.getAmount() + "</TD>");
                if (elem.isPaid()) {
                    out.print("<TD>Cobrado</TD>");
                    out.print("<TD></TD>");
                } else {
                    out.print("<TD>Pendiente</TD>");
                    out.print("<TD><FORM action='FrontController'><INPUT type='hidden' name='command' value='Receipts'><INPUT type='hidden' name='order' value=" + count + "><INPUT type='hidden' name='charged' value=" + elem.getReference() + "><INPUT type='submit' value='Cobrado' class='botonTable'></FORM>");
                }
                count += 1;
                out.print("</TR>");
            }
            out.print("</TABLE><BR>");

        %>
        <FORM action='FrontController'>
            <INPUT type='hidden' name='command' value='Policies'>
            <INPUT type='submit' value='Volver' class='boton'>
        </FORM>
        <jsp:include page="footer.jsp"/>
    </body>
</html>