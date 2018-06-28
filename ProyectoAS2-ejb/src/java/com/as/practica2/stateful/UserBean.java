/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.stateful;

import com.as.practica2.sbEntity.UserFacade;
import com.as.practica2.object.User;
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

/**
 *
 * @author Jorge
 */
@Stateful
@LocalBean
public class UserBean {

    private File file;
    private PrintWriter writing;
    private FileWriter fWritting;
    private boolean print = false;
    private LogBean log;
    private StadisticsBean stadistics;
    Map<String, List<User>> map = new HashMap<String, List<User>>();

    @PostConstruct
    public void UserBean() {
        try {
            file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful3.txt");
            fWritting = new FileWriter(file, true);
            writing = new PrintWriter(fWritting);
            writing.println("UserBean::PostConstruct::UserBean");
            close();
            print = true;
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            log.addFuntion("UserBean::PostConstruct");
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            stadistics.addComponent("UserBean");

        } catch (IOException | NamingException ex) {
            log.addFuntion("UserBean::Excepción PostConstruct");
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addUser(String user, User userAux) {
        stadistics.addComponentUsers("SinUsuario");
        log.addFuntion("UserBean::addUser");
        stadistics.addComponent("UserBean");
        setText("UserBean", "addUser", "SinUsuario");
        List<User> aux;
        if (map.containsKey(user)) {
            aux = map.get(user);
        } else {
            aux = new ArrayList<User>();
        }
        aux.add(userAux);
        map.put(user, aux);
    }

    public boolean checkUserLogin(String auxUser, String auxPass) {
//        try {
//            stadistics.addComponentUsers("SinUsuario");
//            log.addFuntion("UserBean::checkUserLogin");
//            stadistics.addComponent("UserBean");
//            setText("UserBean", "checkUserLogin", "SinUsuario");
//            UserFacade userFacade = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/UserFacade");
//            //com.as.practica2.entity.User user = userFacade.findByName(auxUser);
//            //String pass = user.getPass();
//            if (!pass.equals(auxPass)) {
//                return false;
//            }
//        } catch (NamingException ex) {
//            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        String pass = "";
//        if (map.containsKey(auxUser)) {
//            pass = map.get(auxUser).get(0).getPass();
//            if (!pass.equals(auxPass)) {
//                return false;
//            }
//        } else {
//            return false;
//        }
//        try {
//            StadisticsBean stadisticsBean = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
//            stadisticsBean.addUser();
//        } catch (NamingException ex) {
//            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return true;
    }

    public List<User> getUserList(String user) {
        stadistics.addComponentUsers("SinUsuario");
        log.addFuntion("UserBean::getUserList");
        stadistics.addComponent("UserBean");
        setText("UserBean", "getUserList", "SinUsuario");
        List<User> aux;
        if (map.containsKey(user)) {
            aux = map.get(user);
        } else {
            aux = new ArrayList<User>();
        }
        return aux;
    }

    public void setText(String clase, String metodo, String usuario) {
        if (print) {
            try {
                file = new File("C:\\Users\\Jorge\\Desktop\\AS\\Practica2\\stateful3.txt");
                fWritting = new FileWriter(file, true);
                writing = new PrintWriter(fWritting);
                String mensaje = clase + "::" + metodo + "::" + usuario;
                writing.println(mensaje);
                close();
            } catch (IOException ex) {
                log.addFuntion("UserBean::Error al escribir en el documento.");
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostActivate
    public void postActivate() {
        stadistics.addComponent("UserBean");
        log.addFuntion("UserBean::PostActivate");
        setText("UserBean", "PostActivate", "postActivate");
    }

    @PrePassivate
    public void prePassivate() {
        stadistics.addComponent("UserBean");
        log.addFuntion("UserBean::PrePassivate");
        setText("UserBean", "PrePassivate", "prePassivate");
    }

    @PreDestroy
    public void preDestroy() {
        stadistics.addComponent("UserBean");
        log.addFuntion("UserBean::PreDestroy");
        setText("UserBean", "PreDestroy", "preDestroy");

    }

    @Remove
    public void remove() {
        stadistics.addComponent("UserBean");
        log.addFuntion("UserBean::Remove");
        setText("UserBean", "Remove", "remove");
        close();
    }

    public void close() {
        try {
            fWritting.close();
            writing.close();

        } catch (IOException ex) {
            log.addFuntion("UserBean::Excepción cerrando el documento");
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
