/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.as.practica2.singleton.LogBean;

/**
 *
 * @author Jorge
 */
@Singleton
@LocalBean
public class TimerBean1 {

    private LogBean log;
    private boolean action = false;
    private Map<String, Integer> contador;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void init() {
        try {
            contador = new HashMap<>();
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            timerService.createSingleActionTimer(5000, new TimerConfig());
        } catch (NamingException ex) {
            Logger.getLogger(TimerBean1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void count(String page) {
        if (contador.containsKey(page)) {
            action = true;
            contador.put(page, contador.get(page) + 1);
        } else {
            action = true;
            contador.put(page, 1);
        }
    }

    @Timeout
    public void timeout(Timer timer) {
        if (!action) {
            log.addFuntion("Ningun usuario ha interactuado con el sistema en menos de 5 segundos.");
            timer = timerService.createSingleActionTimer(5000, new TimerConfig());
        } else {
            action = false;
            timer = timerService.createSingleActionTimer(5000, new TimerConfig());
        }

    }

    @PreDestroy
    public void preDestroy() {
        contador = new HashMap<>();
    }
}
