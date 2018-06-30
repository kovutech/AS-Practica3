<%-- 
    Document   : receipts
    Created on : 19-jun-2018, 14:12:10
    Author     : Jorge
--%>

<%@page import="frontController.ReceiptSearch"%>
<%@page import="com.as.practica2.entity.ReceiptState"%>
<%@page import="com.as.practica2.sbEntity.ReceiptStateFacade"%>
<%@page import="com.as.practica2.entity.Products"%>
<%@page import="com.as.practica2.sbEntity.ProductsFacade"%>
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

<FORM action='FrontController' method='get'>
    <TABLE border=1 class='center'>
        <TR>
            <TD colspan='3'>BUSCAR RECIBOS</TD>
            <TD colspan='2'>MODO</TD>
        </TR>
        <TR>
            <TH width="50%"><B>Nombre de cliente</B></TH>
            <TH width="10%"><B>Tipo de póliza<input type="checkbox" name="chkType" value="1"></B></TH>
            <TH width="10%"><B>Orden</B></TH>
            <TH width="10%"><B>JPQL <input type="radio" name="searchMode" value="JPQL" checked="true"></B></TH>
            <TH width="10%"><B>CRITERIA API<input type="radio" name="searchMode" value="CRITERIA"></B></TH>
        </TR>
        <TR>
        <INPUT type='hidden' name='command' value='ReceiptSearch'>
        <INPUT type='hidden' name='search' value='1'>
        <TD><INPUT type='text' name='client' value='' placeholder='Cliente' ></TD>
        <TD>
            <select name="type" required="true">
                <%
                    ProductsFacade productsFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ProductsFacade");
                    List<Products> products = productsFacade.findAll();
                    for (Products elem : products) {
                %>
                <option value="<%=  elem.getName()%>"><%= elem.getName()%></option>");
                <%
                    }
                %>
            </select>
        </TD>
        <TD>
            <select name="order" required="true">
                <option value="asc">Ascendente</option>");
                <option value="desc">Descendente</option>");
            </select>
        </TD>
        <TD colspan="2"><INPUT type="submit" value="Buscar" name="criteria"></TD>
        </TR>
    </TABLE>
</FORM>
<BR>
<BR>
<TABLE border=1 class='center'>
    <TR><TD colspan='8'>LISTADO DE RECIBOS</TD></TR>
    <TR><TD>Cliente</TD><TD>Tipo de Póliza</TD><TD>Nº póliza</TD><TD>Referencia</TD><TD>Fecha de efecto</TD><TD>Importe</TD><TD>Estado</TD></TR>
            <%
                if (request.getParameter("search") != null) {
                    List<Receipt> receipts = new ArrayList<Receipt>();
                    if (request.getParameter("searchMode").equals("JPQL")) {
                        ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
                        String[] params = (String[]) session.getAttribute("searchParams");
                        int currentPage = (Integer) session.getAttribute("currentPage");
                        receipts = receiptFacade.searchReceiptsJPQL(params[0], params[1], params[2], currentPage);
                    } else {
                        System.out.println("CRITERIA");
                    }

                    if (receipts.size() <= 0 || receipts == null || receipts.isEmpty()) {
                        System.out.println("NADA");
                    } else {
                        for (Receipt elem : receipts) {

            %>
    
        <TR>
            <TD><%= elem.getClient()%></TD>
            <TD><%= elem.getTipoPoliza()%></TD>
            <TD><%= elem.getNPoliza()%></TD>
            <TD><%= elem.getReference()%></TD>
            <TD><%= elem.getChargeDate()%></TD>
            <TD><%= elem.getAmount()%></TD>
            <TD><%= elem.getCodState().getName()%></TD>
        </TR>
    
    <%
                }
            }
        }
    %>
</TABLE>
<TABLE border="1"class='center'>
    <tr>
        <td width="90%"></td>
    <FORM action='FrontController' method='get'>
        <INPUT type="hidden" name="command" value="ReceiptSearch">
        <INPUT type="hidden" name="search" value="1">
        <INPUT type="hidden" name="pageAtras" value="1">
        <td width="5%">
            <INPUT type="submit" value="<">
        </td>
    </FORM>
    <FORM action='FrontController' method='get'>
        <INPUT type="hidden" name="command" value="ReceiptSearch">
        <INPUT type="hidden" name="search" value="1">
        <INPUT type="hidden" name="pageAdelante" value="1">
        <td width="5%">
            <INPUT type="submit" value=">">
        </td>
    </FORM>
</tr>
</TABLE>

<jsp:include page="footer.jsp"/>
