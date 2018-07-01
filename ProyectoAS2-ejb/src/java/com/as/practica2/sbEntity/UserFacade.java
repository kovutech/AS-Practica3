/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.User;
import com.as.practica2.singleton.LogBean;
import com.as.practica2.singleton.StadisticsBean;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jorge
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    private LogBean log;
    private StadisticsBean stadistics;
    
    @PersistenceContext(unitName = "ProyectoAS2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User findByName(String s) {
        List<User> user = em.createNamedQuery("User.findByName")
                .setParameter("name", s)
                .getResultList();
        if (user.size() > 0) {
            return user.get(0);
        } else {
            return null;
        }
    }
    
        public void addTrace(String user, String method){
        try {
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            stadistics.addComponentUsers(user);
            log.addFuntion("UserFacade::" + method + "::" + user);
            stadistics.addComponent("UserFacade");
        } catch (NamingException ex) {
            Logger.getLogger(ClientFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
