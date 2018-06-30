/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.entity.Receipt;
import com.as.practica2.sbEntity.ReceiptFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        if (request.getParameter("search") != null) {
            HttpSession session = request.getSession(true);
            if ((request.getParameter("pageAtras") != null) || (request.getParameter("pageAdelante") != null)) {
                int currentPage = (Integer) session.getAttribute("currentPage");
                if (request.getParameter("pageAtras") != null) {
                    currentPage -= 1;
                } else {
                    currentPage += 1;
                }
                session.setAttribute("currentPage", currentPage);
            } else {
                List<Receipt> receipts = new ArrayList<Receipt>();
                try {
                    String client = "";
                    String type = "";
                    String order = "";

                    ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
                    if (request.getParameter("client") != null) {
                        client = request.getParameter("client");
                    }
                    String aux = request.getParameter("chkType");
                    if (aux != null && aux.length() != 0) {
                        type = request.getParameter("type");
                    }
                    order = request.getParameter("order");
                    String[] params = {client, type, order};
                    if (params != null) {
                        session.setAttribute("searchParams", params);

                    }
                    session.setAttribute("currentPage", 1);
                } catch (NamingException ex) {
                    Logger.getLogger(ReceiptSearch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
