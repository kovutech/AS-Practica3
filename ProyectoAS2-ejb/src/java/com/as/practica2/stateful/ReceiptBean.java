/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.stateful;

import com.as.practica2.object.Policy;
import com.as.practica2.object.Receipt;
import com.as.practica2.singleton.LogBean;
import com.as.practica2.singleton.StadisticsBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
@Stateful
@LocalBean
public class ReceiptBean {

    private File file;
    private PrintWriter writing;
    private FileWriter fWritting;
    private boolean print = false;
    private LogBean log;
    private StadisticsBean stadistics;

    Map<String, List<Receipt>> map = new HashMap<String, List<Receipt>>();

    @PostConstruct
    public void ReceiptBean() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful4.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("ReceiptBean::PostConstruct::ReceiptBean");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("ReceiptBean::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("ReceiptBean");

        } catch (IOException | NamingException ex) {
            log.addFuntion("ReceiptBean::Excepción PostConstruct");
            Logger.getLogger(ReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, List<Receipt>> receiptPaid(String currentPolicy, int order, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ReceiptBean::receiptPaid");
        stadistics.addComponent("ReceiptBean");
        setText("ReceiptBean", "receiptPaid", user);
        List<Receipt> receipts = getReceiptList(currentPolicy, user);
        receipts.get(order).setPaid(true);
        map.put(currentPolicy, receipts);
        return map;
    }

    public void addReceipt(String id, Receipt receiptAux, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ReceiptBean::addReceipt");
        stadistics.addComponent("ReceiptBean");
        setText("ReceiptBean", "addReceipt", user);
        List<Receipt> aux;
        if (map.containsKey(id)) {
            aux = map.get(id);
        } else {
            aux = new ArrayList<Receipt>();
        }
        aux.add(receiptAux);
        map.put(id, aux);
    }

    public List<Receipt> getReceiptList(String id, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ReceiptBean::getReceiptList");
        stadistics.addComponent("ReceiptBean");
        setText("ReceiptBean", "getReceiptList", user);
        List<Receipt> aux;
        if (map.containsKey(id)) {
            aux = map.get(id);
        } else {
            aux = new ArrayList<Receipt>();
        }
        return aux;
    }

    public Map<String, List<Receipt>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Receipt>> aux) {
        map = aux;
    }

    public void setText(String clase, String metodo, String usuario) {
        if (print) {
            try {
                file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful4.txt");
                fWritting = new FileWriter(file, true);
                writing = new PrintWriter(fWritting);
                String mensaje = clase + "::" + metodo + "::" + usuario;
                writing.println(mensaje);
                close();
            } catch (IOException ex) {
                log.addFuntion("ReceiptBean::Error al escribir en el documento.");
                Logger.getLogger(ReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("ReceiptBean");
        log.addFuntion("ReceiptBean::PostActivate");
        setText("ReceiptBean", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("ReceiptBean");
        log.addFuntion("ReceiptBean::PrePassivate");
        setText("ReceiptBean", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("ReceiptBean");
        log.addFuntion("ReceiptBean::PreDestroy");
        setText("ReceiptBean", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("ReceiptBean");
        log.addFuntion("ReceiptBean::Remove");
        setText("ReceiptBean", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("ReceiptBean::Excepción cerrando el documento");
            Logger.getLogger(ReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
