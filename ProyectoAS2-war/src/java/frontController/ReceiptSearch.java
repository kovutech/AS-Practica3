/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.sbEntity.ReceiptFacade;
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

/**
 *
 * @author Jorge
 */
public class ReceiptSearch extends FrontCommand {

    @Override
    public void process() {
        try {
            search();
            forward("/receiptSearch.jsp");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
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

    public void search() {
        String client = "";
        String type = "";
        String state = "";
        String order = "";

        try {
            ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
        } catch (NamingException ex) {
            Logger.getLogger(ReceiptSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (request.getParameter("search") != null) {
            ReceiptFacade receiptFacade = null;
            if (request.getParameter("client") != null) {
                client = request.getParameter("client");
            }
            String aux = request.getParameter("chkType");
            if (aux != null && aux.length() != 0) {
                type = request.getParameter("type");
            }
            aux = request.getParameter("chkState");
            if (aux != null && aux.length() != 0) {
                state = request.getParameter("state");
            }
            order = request.getParameter("order");
            System.out.println("Cliente: " + client);
            System.out.println("Tipo: " + type);
            System.out.println("Estado: " + state);
            System.out.println("Orden: " + order);
            if (request.getParameter("searchMode").equals("JPQL")) {
                receiptFacade.searchReceiptsJPQL(client, type, state, order);
            } else {
                System.out.println("CRITERIA");
            }
        }
    }
}
