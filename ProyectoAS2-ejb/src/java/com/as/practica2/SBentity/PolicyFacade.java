/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.Client;
import com.as.practica2.entity.Policy;
import com.as.practica2.singleton.LogBean;
import com.as.practica2.singleton.StadisticsBean;
import java.util.ArrayList;
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
public class PolicyFacade extends AbstractFacade<Policy> {

    private LogBean log;
    private StadisticsBean stadistics;
    
    @PersistenceContext(unitName = "ProyectoAS2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PolicyFacade() {
        super(Policy.class);
    }
    
    public Policy findByIdentification(String s) {
        List<Policy> policy = em.createNamedQuery("Policy.findByIdentification")
                .setParameter("identification", s)
                .getResultList();
        if (policy.size() > 0) {
            return policy.get(0);
        } else {
            return null;
        }
    }

    public List<Policy> findByCodClient(Client c) {
        List<Policy> policy = em.createNamedQuery("Policy.findByCodClient")
                .setParameter("codClient", c)
                .getResultList();
        System.out.println(policy.getClass().getName());
        if (policy.size() > 0) {
            return policy;
        } else {
            return new ArrayList<Policy>();
        }
    }
    
     public void addTrace(String user, String method){
        try {
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            stadistics.addComponentUsers(user);
            log.addFuntion("PolicyFacade::" + method + "::" + user);
            stadistics.addComponent("PolicyFacade");
        } catch (NamingException ex) {
            Logger.getLogger(ClientFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
