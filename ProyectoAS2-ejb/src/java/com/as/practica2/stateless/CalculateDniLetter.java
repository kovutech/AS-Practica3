/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.stateless;

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
import com.as.practica2.singleton.StadisticsBean;
import com.as.practica2.singleton.LogBean;

/**
 *
 * @author Jorge
 */
@Stateless
@LocalBean
public class CalculateDniLetter {

    private File file;
    private PrintWriter writing;
    private FileWriter fWritting;
    private boolean print = false;
    private LogBean log;
    private StadisticsBean stadistics;

    @PostConstruct
    public void CalculateDniLetter() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateless1.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("CalculateDniLetter::PostConstruct::CalculateDniLetter");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("CalculateDniLetter::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("CalculateDniLetter");

        } catch (IOException | NamingException ex) {
            log.addFuntion("CalculateDniLetter::Excepción PostConstruct");
            Logger.getLogger(CalculateDniLetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDniLetter(String str, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("CalculateDniLetter::getDniLetter");
        stadistics.addComponent("CalculateDniLetter");
        setText("CalculateDniLetter", "getDniLetter", user);
        char[] LETTERS = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        if (str.matches("[0-9]{8}")) {
            int num = Integer.parseInt(str.replaceAll("[^0-9]", ""));
            return "" + LETTERS[num % LETTERS.length];
        }
        return "-";
    }

    public void setText(String clase, String metodo, String usuario) {
        if (print) {
            try {
                file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateless1.txt");
                fWritting = new FileWriter(file, true);
                writing = new PrintWriter(fWritting);
                String mensaje = clase + "::" + metodo + "::" + usuario;
                writing.println(mensaje);
                close();
            } catch (IOException ex) {
                log.addFuntion("CalculateDniLetter::Error al escribir en el documento.");
                Logger.getLogger(CalculateDniLetter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("CalculateDniLetter");
        log.addFuntion("CalculateDniLetter::PostActivate");
        setText("CalculateDniLetter", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("CalculateDniLetter");
        log.addFuntion("CalculateDniLetter::PrePassivate");
        setText("CalculateDniLetter", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("CalculateDniLetter");
        log.addFuntion("CalculateDniLetter::PreDestroy");
        setText("CalculateDniLetter", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("CalculateDniLetter");
        log.addFuntion("CalculateDniLetter::Remove");
        setText("CalculateDniLetter", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("CalculateDniLetter::Excepción cerrando el documento");
            Logger.getLogger(CalculateDniLetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
