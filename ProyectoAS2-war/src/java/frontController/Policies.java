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
import com.as.practica2.entity.User;
import com.as.practica2.sbEntity.ClientFacade;
import com.as.practica2.sbEntity.PayMethodFacade;
import com.as.practica2.sbEntity.PolicyFacade;
import com.as.practica2.sbEntity.ProductsFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                User user = (User) session.getAttribute("user");
                Client client = (Client) session.getAttribute("client");
                PolicyFacade policyFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PolicyFacade");

                ProductsFacade productsFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ProductsFacade");
                Products product = productsFacade.findByIdProduct(Integer.parseInt(request.getParameter("type")));
                productsFacade.addTrace(user.getName(), "findByIdProduct");

                PayMethodFacade payMethodFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PayMethodFacade");
                PayMethod payMethod = payMethodFacade.findByIdPayMethod(Integer.parseInt(request.getParameter("payRange")));
                payMethodFacade.addTrace(user.getName(), "findByIdPayMethod");

                policyFacade.create(new Policy(null, client, product, payMethod, parseDate(request.getParameter("from")), parseDate(request.getParameter("to")), Float.parseFloat(request.getParameter("price")), request.getParameter("identification")));
                policyFacade.addTrace(user.getName(), "create");
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
            deletePolicy();
            addClient();
            forward("/policies.jsp");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkClient() {
        if (request.getParameter("listPolicy") != null) {
            try {
                HttpSession session = request.getSession(true);
                User user = (User) session.getAttribute("user");
                ClientFacade clientFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ClientFacade");
                Client client = clientFacade.findByIdentification(request.getParameter("identification"));
                clientFacade.addTrace(user.getName(), "findByIdentification");
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

    public void deletePolicy() {
        if (request.getParameter("deletePolicy") != null) {
            try {
                HttpSession session = request.getSession(true);
                User user = (User) session.getAttribute("user");
                PolicyFacade policyFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PolicyFacade");
                Policy policy = policyFacade.findByIdentification(request.getParameter("deletePolicy"));
                policyFacade.addTrace(user.getName(), "findByIdentification");
                if (policy != null) {
                    policyFacade.remove(policy);
                     policyFacade.addTrace(user.getName(), "remove");
                }
            } catch (NamingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
