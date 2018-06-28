<%-- 
    Document   : main
    Created on : 24-feb-2018, 16:51:22
    Author     : Jorge
--%>

<%@page import="com.as.practica2.entity.Client"%>
<%@page import="com.as.practica2.entity.User"%>
<%@page import="com.as.practica2.sbEntity.ClientFacade"%>
<%@page import="com.as.practica2.singleton.StadisticsBean"%>
<%@page import="com.as.practica2.singleton.LogBean"%>
<%@page import="com.as.practica2.stateful.ClientBean"%>
<%@page import="com.as.practica2.stateless.CalculateDniLetter"%>
<%@page import="java.util.ResourceBundle.Control"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("user") == null) {
        RequestDispatcher r = request.getRequestDispatcher("index.jsp");
        r.forward(request, response);
    }

    LogBean logBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
    logBean.addFuntion("main.jsp");

    StadisticsBean estadisticasBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
    estadisticasBean.addPage("main.jsp");
%>
<jsp:include page="headerA.jsp"/>

<FORM action='FrontController' method='post'>
    <TABLE border=1 class='center'>
        <TR><TD colspan='5'>AÑADIR CLIENTE</TD></TR>
        <TR><TH><B>Dni (sin letra)</B></TH><TH><B>Nombre</B></TH><TH><B>Apellido</B></TH><TH><B>Teléfono</B></TH><TH><B>Insertar</B></TH></TR>
        <TR>
        <INPUT type='hidden' name='command' value='Main'>
        <INPUT type='hidden' name='addClient' value='1'>
        <TD><INPUT type='text' pattern='\d{8}' name='identification' value='' placeholder='Dni' maxlength='8' required="true"></TD>
        <TD><INPUT type='text' name='name' value='' placeholder='Nombre' required="true"></TD>
        <TD><INPUT type='text' name='surName' value='' placeholder='Apellido' required="true"></TD>
        <TD><INPUT type='text' pattern='\d{9}' name='telephone' value='' placeholder='Telefono' maxlength='9' required="true"></TD>
        <TD><INPUT type='submit' value='Añadir' class='botonTable'></TD>
        </TR>
    </TABLE>
</FORM>
<br>
<br>

<%
    CalculateDniLetter calculateDniLetter = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/CalculateDniLetter");
    ClientBean clientList = (ClientBean) session.getAttribute("clientList");

    User user = (User) session.getAttribute("user");

    out.print("<TABLE border=1 class='center'>");
    out.print("<TR><TD colspan='6'>LISTADO DE CLIENTES</TD></TR>");
    out.print("<TR><TH><B>Dni</B></TH><TH><B>Nombre</B></TH><TH><B>Apellido</B></TH><TH><B>Teléfono</B></TH><TH><B>Ficha</B></TH><TH><B>Eliminar</B></TH></TR>");
    ClientFacade clientFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientFacade");
    List<Client> clients = clientFacade.findAll();
    if (clients != null) {
        for (Client elem : clients) {
            if (elem.getCodUser().getIdUser() == user.getIdUser()) {
                out.print("<TR>");
                out.print("<TD>" + elem.getIdentification() + " " + calculateDniLetter.getDniLetter(String.valueOf(elem.getIdentification()), user.getName()) + "</TD>");
                out.print("<TD>" + elem.getName() + "</TD>");
                out.print("<TD>" + elem.getSurName() + "</TD>");
                out.print("<TD>" + elem.getTelephone() + "</TD>");
                out.print("<FORM action='FrontController' method='post'>");
                out.print("<INPUT type='hidden' name='command' value='Policies'>");
                out.print("<INPUT type='hidden' name='listPolicy' value='1'>");
                out.print("<INPUT type='hidden' name='dni' value='" + elem.getIdentification() + "'>");
                out.print("<INPUT type='hidden' name='nombre' value='" + elem.getName() + "'>");
                out.print("<INPUT type='hidden' name='apellido' value='" + elem.getSurName() + "'>");
                out.print("<INPUT type='hidden' name='telefono' value='" + elem.getTelephone() + "'>");
                out.print("<TD><INPUT type='submit' value='Acceder' class='botonTable'></TD></FORM>");
                out.print("<FORM action='FrontController' method='post'>");
                out.print("<INPUT type='hidden' name='command' value='Main'>");
                out.print("<INPUT type='hidden' name='deleteClient' value='" + elem.getIdentification() + "'>");
                out.print("<TD><INPUT type='submit' value='Eliminar' class='botonTable'></TD>"
                        + "</FORM>");
                out.print("</TR>");
            }
        }
    }
    out.print("</TABLE>");

%>
<jsp:include page="footer.jsp"/>
