package com.as.practica2.timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
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
public class TimerBean2 {

    private File document;
    private PrintWriter escritura;
    private FileWriter dEscritura;

    private LogBean log;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void init() {
        try {
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            timerService.createSingleActionTimer(5000, new TimerConfig());
        } catch (NamingException ex) {
            Logger.getLogger(TimerBean1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Timeout
    public void timeout(Timer timer) {
        setText();
        timer = timerService.createSingleActionTimer(5000, new TimerConfig());
    }

    public void setText() {
        try {
            String ruta = "C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\contenidoLogBean.txt";
            File fichero = new File(ruta);
            fichero.delete();

            document = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\contenidoLogBean.txt");
            dEscritura = new FileWriter(document, true);
            escritura = new PrintWriter(dEscritura);

            List<String> mensajes = log.getTrazaTimer();
            for (String t : mensajes) {
                String mensaje = t;
                escritura.println(mensaje);
            }
            dEscritura.close();
            escritura.close();
        } catch (IOException ex) {
            Logger.getLogger(TimerBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void sheduleTimer() {

    }

    @PreDestroy
    public void remove() {
        try {
            dEscritura.close();
            escritura.close();
        } catch (IOException ex) {
            Logger.getLogger(TimerBean2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
