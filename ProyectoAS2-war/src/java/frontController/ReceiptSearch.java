/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.entity.Receipt;
import com.as.practica2.entity.User;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (request.getParameter("search") != null) {
            if ((request.getParameter("pageAtras") != null) || (request.getParameter("pageAdelante") != null)) {
                int currentPage = (Integer) session.getAttribute("currentPage");
                if (request.getParameter("pageAtras") != null) {
                    currentPage -= 1;
                } else {
                    if (currentPage < Integer.parseInt(String.valueOf(session.getAttribute("maxPages")))) {
                        currentPage += 1;
                    }
                }
                session.setAttribute("currentPage", currentPage);
            } else {
                List<Receipt> receipts = new ArrayList<Receipt>();
                String client = "";
                String type = "";
                String order = "";
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
                if (request.getParameter("searchMode") != null) {
                    session.setAttribute("mode", request.getParameter("searchMode"));
                }
                session.setAttribute("currentPage", 1);
            }
        }
    }
}
