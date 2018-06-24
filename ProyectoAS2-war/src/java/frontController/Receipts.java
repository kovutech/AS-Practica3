/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.object.Policy;
import com.as.practica2.object.Receipt;
import com.as.practica2.stateful.PolicyBean;
import com.as.practica2.stateful.ReceiptBean;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class Receipts extends FrontCommand {

    @Override
    public void process() {
        try {
            currentPolicy();
            addReceipt();
            chargeReceipt();
            forward("/receipts.jsp");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UnknownCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chargeReceipt() {
        if (request.getParameter("charged") != null) {
            HttpSession session = request.getSession(true);
            String user = (String) session.getAttribute("user");
            ReceiptBean receiptList = (ReceiptBean) session.getAttribute("receiptList");
            String currentPolicy = (String) session.getAttribute("currentPolicy");
            receiptList.setMap(receiptList.receiptPaid(currentPolicy, Integer.valueOf(request.getParameter("order")), user));
            session.setAttribute("receiptList", receiptList);

//            List<Receipt> receipts = receiptList.getReceiptList(currentPolicy, clientData.get(2));
//            receipts.get(Integer.valueOf(request.getParameter("order"))).setPaid(true);
//            Map<String,List<Receipt>> map = receiptList.getMap();
//            map.put(currentPolicy, receipts);
//            receiptList.setMap(map);
//            session.setAttribute("receiptList", receiptList);
        }
    }

    public void currentPolicy() {
        HttpSession session = request.getSession(true);
        if (request.getParameter("idPolicy") != null) {
            session.setAttribute("currentPolicy", request.getParameter("idPolicy"));
        }
    }

    public void addReceipt() {
        if (request.getParameter("addReceipt") != null) {
            HttpSession session = request.getSession(true);
            ReceiptBean receiptList = (ReceiptBean) session.getAttribute("receiptList");
            String currentPolicy = (String) session.getAttribute("currentPolicy");
            receiptList.addReceipt(currentPolicy, new Receipt(request.getParameter("id"), request.getParameter("date"), request.getParameter("amount"), Boolean.valueOf(request.getParameter("paid"))), (String) session.getAttribute("user"));
            session.setAttribute("receiptList", receiptList);
        }
    }
}
