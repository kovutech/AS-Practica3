/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.stateless;

import com.as.practica2.singleton.LogBean;
import com.as.practica2.singleton.StadisticsBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Jorge
 */
@Stateless
@LocalBean
public class ClientLevel {

    private File file;
    private PrintWriter writing;
    private FileWriter fWritting;
    private boolean print = false;
    private LogBean log;
    private StadisticsBean stadistics;

    @PostConstruct
    public void ClientLevel() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateless2.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("ClientLevel::PostConstruct::ClientLevel");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("ClientLevel::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("ClientLevel");

        } catch (IOException | NamingException ex) {
            log.addFuntion("ClientLevel::Excepción PostConstruct");
            Logger.getLogger(ClientLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getClientLevel(int numPolicies, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("ClientLevel::getClientLevel");
        stadistics.addComponent("ClientLevel");
        setText("ClientLevel", "getClientLevel", user);
        if (numPolicies >= 8) {
            return "A";
        }
        if (numPolicies >= 5) {
            return "B";
        }
        if (numPolicies >= 1) {
            return "C";
        }
        return "Sin pólizas";
    }

    public void setText(String clase, String metodo, String usuario) {
        if (print) {
            try {
                file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateless2.txt");
                fWritting = new FileWriter(file, true);
                writing = new PrintWriter(fWritting);
                String mensaje = clase + "::" + metodo + "::" + usuario;
                writing.println(mensaje);
                close();
            } catch (IOException ex) {
                log.addFuntion("ClientLevel::Error al escribir en el documento.");
                Logger.getLogger(ClientLevel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("ClientLevel");
        log.addFuntion("ClientLevel::PostActivate");
        setText("ClientLevel", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("ClientLevel");
        log.addFuntion("ClientLevel::PrePassivate");
        setText("ClientLevel", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("ClientLevel");
        log.addFuntion("ClientLevel::PreDestroy");
        setText("ClientLevel", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("ClientLevel");
        log.addFuntion("ClientLevel::Remove");
        setText("ClientLevel", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("ClientLevel::Excepción cerrando el documento");
            Logger.getLogger(ClientLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
