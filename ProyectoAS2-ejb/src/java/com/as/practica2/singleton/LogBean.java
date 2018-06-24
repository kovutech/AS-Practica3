/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.singleton;

import java.util.ArrayList;
import java.util.List;
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
public class LogBean {

    private List<String> traza;
    private TimerBean1 timer1;

    @PostConstruct
    public void init() {
        traza = new ArrayList<>();
        try {
            timer1 = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/TimerBean1");
        } catch (NamingException ex) {
            Logger.getLogger(LogBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addFuntion(String ristra) {
        timer1.count("LogBean");
        traza.add(ristra);
    }

    public List<String> getTraza() {
        timer1.count("LogBean");
        return traza;
    }

    public List<String> getTrazaTimer() {
        return traza;
    }

    @PreDestroy
    public void preDestroy() {
        traza.clear();
    }

}
