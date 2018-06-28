/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.entity.Client;
import com.as.practica2.entity.PayMethod;
import com.as.practica2.entity.Products;
import com.as.practica2.entity.Policy;
import com.as.practica2.sbEntity.ClientFacade;
import com.as.practica2.sbEntity.PayMethodFacade;
import com.as.practica2.sbEntity.PolicyFacade;
import com.as.practica2.sbEntity.ProductsFacade;
import com.as.practica2.stateful.PolicyBean;
import static com.sun.xml.bind.util.CalendarConv.formatter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class Policies extends FrontCommand {

    public void addClient() {
        if (request.getParameter("addPolicy") != null) {
            try {
                HttpSession session = request.getSession(true);
                Client client = (Client) session.getAttribute("client");
                PolicyFacade policyFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PolicyFacade");

                ProductsFacade productsFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ProductsFacade");
                Products product = productsFacade.findByIdProduct(Integer.parseInt(request.getParameter("type")));

                PayMethodFacade payMethodFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PayMethodFacade");
                PayMethod payMethod = payMethodFacade.findByIdPayMethod(Integer.parseInt(request.getParameter("payRange")));

                policyFacade.create(new Policy(null, client, product, payMethod, parseDate(request.getParameter("from")), parseDate(request.getParameter("to")), Float.parseFloat(request.getParameter("price")), request.getParameter("identification")));

            } catch (NamingException ex) {
                Logger.getLogger(Policies.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String parseDate(String s) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
            DateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
            return dateF.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Policies.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void process() {
        try {
            checkClient();
            deleteClient();
            addClient();
            forward("/policies.jsp");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkClient() {
        if (request.getParameter("listPolicy") != null) {
            try {
                ClientFacade clientFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientFacade");
                Client client = clientFacade.findByIdentification(request.getParameter("identification"));
                HttpSession session = request.getSession(true);
                if (client != null) {
                    session.setAttribute("client", client);
                } else {
                    session.removeAttribute("client");
                }
            } catch (NamingException ex) {
                Logger.getLogger(Policies.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteClient() {

        if (request.getParameter("deletePolicy") != null) {
            try {
                PolicyFacade policyFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PolicyFacade");
                Policy policy = policyFacade.findByIdentification(request.getParameter("deletePolicy"));
                if (policy != null) {
                    policyFacade.remove(policy);
                }
            } catch (NamingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
