package com.as.practica2.stateless;

import com.as.practica2.singleton.LogBean;
import com.as.practica2.singleton.StadisticsBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

@Stateless
@LocalBean
public class SellRecommendation {

    private File file;
    private PrintWriter writing;
    private FileWriter fWritting;
    private boolean print = false;
    private LogBean log;
    private StadisticsBean stadistics;

    @PostConstruct
    public void SellRecommendation() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateless3.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("SellRecommendation::PostConstruct::SellRecommendation");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("SellRecommendation::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("SellRecommendation");

        } catch (IOException | NamingException ex) {
            log.addFuntion("SellRecommendation::Excepción PostConstruct");
            Logger.getLogger(SellRecommendation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getRecommendation(List<String> stringPolicies, String user) {
        stadistics.addComponentUsers(user);
        log.addFuntion("SellRecommendation::getRecommendation");
        stadistics.addComponent("SellRecommendation");
        setText("SellRecommendation", "getRecommendation", user);
        String[] allTypesInsurances = {"Accidentes", "Ahorro", "Autos", "Decesos", "Embarcaciones", "Hogar", "Motos", "Salud", "Viajes", "Vida"};
        List<String> auxTypesInsurances = new ArrayList<String>();

        for (String elemA : allTypesInsurances) {
            Boolean bool = true;
            for (String elemB : stringPolicies) {
                if (elemA.equals(elemB)) {
                    bool = false;
                }
            }
            if (bool == true) {
                auxTypesInsurances.add(elemA);
            }
        }
        return auxTypesInsurances;
    }

    public void setText(String clase, String metodo, String usuario) {
        if (print) {
            try {
                file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateless3.txt");
                fWritting = new FileWriter(file, true);
                writing = new PrintWriter(fWritting);
                String mensaje = clase + "::" + metodo + "::" + usuario;
                writing.println(mensaje);
                close();
            } catch (IOException ex) {
                log.addFuntion("SellRecommendation::Error al escribir en el documento.");
                Logger.getLogger(SellRecommendation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("SellRecommendation");
        log.addFuntion("SellRecommendation::PostActivate");
        setText("SellRecommendation", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("SellRecommendation");
        log.addFuntion("SellRecommendation::PrePassivate");
        setText("SellRecommendation", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("SellRecommendation");
        log.addFuntion("SellRecommendation::PreDestroy");
        setText("SellRecommendation", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("SellRecommendation");
        log.addFuntion("SellRecommendation::Remove");
        setText("SellRecommendation", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("SellRecommendation::Excepción cerrando el documento");
            Logger.getLogger(SellRecommendation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
