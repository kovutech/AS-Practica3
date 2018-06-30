/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontController;

import com.as.practica2.entity.Client;
import com.as.practica2.entity.Policy;
import com.as.practica2.entity.Receipt;
import com.as.practica2.entity.ReceiptState;
import com.as.practica2.sbEntity.PolicyFacade;
import com.as.practica2.sbEntity.ReceiptFacade;
import com.as.practica2.sbEntity.ReceiptStateFacade;
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
public class Receipts extends FrontCommand {

    @Override
    public void process() {
        try {
            deletePolicy();
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
            try {
                ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
                Receipt receipt = receiptFacade.findByIdReceipt(Integer.parseInt(request.getParameter("charged")));
                ReceiptStateFacade receiptStateFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptStateFacade");
                ReceiptState receiptState = receiptStateFacade.findByName("Cobrado");
                receiptFacade.chargedReceipt(receipt, receiptState);
            } catch (NamingException ex) {
                Logger.getLogger(Receipts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addReceipt() {
        if (request.getParameter("addReceipt") != null) {
            try {
                HttpSession session = request.getSession(true);
                Policy policy = (Policy) session.getAttribute("policy");
                Client client = (Client) session.getAttribute("client");
                ReceiptStateFacade receiptStateFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptStateFacade");
                ReceiptState receiptState = receiptStateFacade.findByName(request.getParameter("paid"));
                ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
                receiptFacade.addReceipt(policy, receiptState, parseDate(request.getParameter("date")), request.getParameter("amount"), request.getParameter("identification"),
                        client.getName(), policy.getCodProduct().getName(), policy.getIdentification());
            } catch (NamingException ex) {
                Logger.getLogger(Receipts.class.getName()).log(Level.SEVERE, null, ex);
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

    public void currentPolicy() {
        if (request.getParameter("newReceipt") != null) {
            try {
                PolicyFacade policyFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/PolicyFacade");
                com.as.practica2.entity.Policy policy = policyFacade.findByIdentification(request.getParameter("idPolicy"));
                HttpSession session = request.getSession(true);
                session.setAttribute("policy", policy);
            } catch (NamingException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deletePolicy() {
        if (request.getParameter("deleteReceipt") != null) {
            try {
                ReceiptFacade receiptFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/ReceiptFacade");
                Receipt receipt = receiptFacade.findByIdReceipt(Integer.parseInt(request.getParameter("deleteReceipt")));
                receiptFacade.deleteReceipt(receipt);
            } catch (NamingException ex) {
                Logger.getLogger(Receipts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
