/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.stateful;

import com.as.practica2.object.Policy;
import com.as.practica2.object.ReceiptA;;
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

    Map<String, List<ReceiptA>> map = new HashMap<String, List<ReceiptA>>();

    @PostConstruct
    public void ReceiptABean() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful4.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("ReceiptABean::PostConstruct::ReceiptABean");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("ReceiptABean::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("ReceiptABean");

        } catch (IOException | NamingException ex) {
            log.addFuntion("ReceiptABean::Excepción PostConstruct");
            Logger.getLogger(ReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, List<ReceiptA>> receiptPaid(String currentPolicy, int order, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ReceiptABean::receiptPaid");
        stadistics.addComponent("ReceiptABean");
        setText("ReceiptABean", "receiptPaid", user);
        List<ReceiptA> receipts = getReceiptAList(currentPolicy, user);
        receipts.get(order).setPaid(true);
        map.put(currentPolicy, receipts);
        return map;
    }

    public void addReceiptA(String id, ReceiptA receiptAux, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ReceiptABean::addReceiptA");
        stadistics.addComponent("ReceiptABean");
        setText("ReceiptABean", "addReceiptA", user);
        List<ReceiptA> aux;
        if (map.containsKey(id)) {
            aux = map.get(id);
        } else {
            aux = new ArrayList<ReceiptA>();
        }
        aux.add(receiptAux);
        map.put(id, aux);
    }

    public List<ReceiptA> getReceiptAList(String id, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ReceiptABean::getReceiptAList");
        stadistics.addComponent("ReceiptABean");
        setText("ReceiptABean", "getReceiptAList", user);
        List<ReceiptA> aux;
        if (map.containsKey(id)) {
            aux = map.get(id);
        } else {
            aux = new ArrayList<ReceiptA>();
        }
        return aux;
    }

    public Map<String, List<ReceiptA>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<ReceiptA>> aux) {
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
                log.addFuntion("ReceiptABean::Error al escribir en el documento.");
                Logger.getLogger(ReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("ReceiptABean");
        log.addFuntion("ReceiptABean::PostActivate");
        setText("ReceiptABean", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("ReceiptABean");
        log.addFuntion("ReceiptABean::PrePassivate");
        setText("ReceiptABean", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("ReceiptABean");
        log.addFuntion("ReceiptABean::PreDestroy");
        setText("ReceiptABean", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("ReceiptABean");
        log.addFuntion("ReceiptABean::Remove");
        setText("ReceiptABean", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("ReceiptABean::Excepción cerrando el documento");
            Logger.getLogger(ReceiptBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
