/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.as.practica2.timer.TimerBean1;

/**
 *
 * @author Jorge
 */
@Singleton
@LocalBean
public class StadisticsBean {

    private int users;
    private Map<String, Integer> contadorPages;
    private Map<String, Integer> contadorComponents;
    private Map<String, Integer> contadorComponentUsers;

    private TimerBean1 timer1;

    @PostConstruct
    public void init() {
        try {
            users = 0;
            contadorPages = new HashMap<>();
            contadorComponents = new HashMap<>();
            contadorComponentUsers = new HashMap<>();
            timer1 = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/TimerBean1");
        } catch (NamingException ex) {
            Logger.getLogger(StadisticsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void preDestroy() {
        users = 0;
        contadorPages = new HashMap<>();
        contadorComponents = new HashMap<>();

    }

    public Map<String, Integer> getComponentUsers() {
        timer1.count("EstadisticasBean");
        return contadorComponentUsers;
    }

    public Map<String, Integer> getPageCount() {
        timer1.count("EstadisticasBean");
        return contadorPages;
    }

    public Map<String, Integer> getComponentCount() {
        timer1.count("EstadisticasBean");
        return contadorComponents;
    }

    //Guarda una pagina y su cuenta
    public void addPage(String page) {
        timer1.count("EstadisticasBean");
        if (contadorPages.containsKey(page)) {
            contadorPages.put(page, contadorPages.get(page) + 1);
        } else {
            contadorPages.put(page, 1);
        }
    }

    //Guarda el usuario y el numero de componentes que visita
    public void addComponentUsers(String user) {
        timer1.count("EstadisticasBean");
        if (contadorComponentUsers.containsKey(user)) {
            contadorComponentUsers.put(user, contadorComponentUsers.get(user) + 1);
        } else {
            contadorComponentUsers.put(user, 1);
        }
    }

    //Guarda un componente y su cuenta
    public void addComponent(String page) {
        timer1.count("EstadisticasBean");
        if (contadorComponents.containsKey(page)) {
            contadorComponents.put(page, contadorComponents.get(page) + 1);
        } else {
            contadorComponents.put(page, 1);
        }
    }

    public void addUser() {
        timer1.count("EstadisticasBean");
        users += 1;
    }

    public int getUsers() {
        timer1.count("EstadisticasBean");
        return users;
    }
}
