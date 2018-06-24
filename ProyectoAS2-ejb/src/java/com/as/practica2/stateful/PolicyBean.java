/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.stateful;

import com.as.practica2.object.Policy;
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
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateful
@LocalBean
public class PolicyBean {

    private File file;
    private PrintWriter writing;
    private FileWriter fWritting;
    private boolean print = false;
    private LogBean log;
    private StadisticsBean stadistics;
    Map<String, List<Policy>> map = new HashMap<String, List<Policy>>();

    @PostConstruct
    public void PolicyBean() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful2.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("PolicyBean::PostConstruct::PolicyBean");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("PolicyBean::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("PolicyBean");

        } catch (IOException | NamingException ex) {
            log.addFuntion("PolicyBean::Excepción PostConstruct");
            Logger.getLogger(PolicyBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addPolicy(String id, Policy policyAux, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("PolicyBean::addPolicy");
        stadistics.addComponent("PolicyBean");
        setText("PolicyBean", "addPolicy", user);
        List<Policy> aux;
        if (map.containsKey(id)) {
            aux = map.get(id);
        } else {
            aux = new ArrayList<Policy>();
        }
        aux.add(policyAux);
        map.put(id, aux);
    }

    public void deletePolicy(String id, String PolicyId, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("PolicyBean::deletePolicy");
        stadistics.addComponent("PolicyBean");
        setText("PolicyBean", "deletePolicy", user);
        List<Policy> aux = new ArrayList<Policy>();
        aux = map.get(id);
        for (int i = 0; i < aux.size(); i++) {
            if (aux.get(i).getId().equals(id)) {
                aux.remove(i);
            }
        }
        map.put(id, aux);
    }

    public List<Policy> getPolicyList(String id, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("PolicyBean::getPolicyList");
        stadistics.addComponent("PolicyBean");
        setText("PolicyBean", "getPolicyList", user);
        List<Policy> aux;
        if (map.containsKey(id)) {
            aux = map.get(id);
        } else {
            aux = new ArrayList<Policy>();
        }
        return aux;
    }

    public void setText(String clase, String metodo, String usuario) {
        if (print) {
            try {
                file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful2.txt");
                fWritting = new FileWriter(file, true);
                writing = new PrintWriter(fWritting);
                String mensaje = clase + "::" + metodo + "::" + usuario;
                writing.println(mensaje);
                close();
            } catch (IOException ex) {
                log.addFuntion("PolicyBean::Error al escribir en el documento.");
                Logger.getLogger(PolicyBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("PolicyBean");
        log.addFuntion("PolicyBean::PostActivate");
        setText("PolicyBean", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("PolicyBean");
        log.addFuntion("PolicyBean::PrePassivate");
        setText("PolicyBean", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("PolicyBean");
        log.addFuntion("PolicyBean::PreDestroy");
        setText("PolicyBean", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("PolicyBean");
        log.addFuntion("PolicyBean::Remove");
        setText("PolicyBean", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("PolicyBean::Excepción cerrando el documento");
            Logger.getLogger(PolicyBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
