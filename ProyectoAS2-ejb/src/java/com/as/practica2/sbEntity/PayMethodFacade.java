/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.PayMethod;
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
public class PayMethodFacade extends AbstractFacade<PayMethod> {

    private LogBean log;
    private StadisticsBean stadistics;
    
    @PersistenceContext(unitName = "ProyectoAS2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayMethodFacade() {
        super(PayMethod.class);
    }
    
    public PayMethod findByIdPayMethod(int n) {
        List<PayMethod> payMethod = em.createNamedQuery("PayMethod.findByIdPayMethod")
                .setParameter("idpayMethod", n)
                .getResultList();
        if (payMethod.size() > 0) {
            return payMethod.get(0);
        } else {
            return null;
        }
    }
    
     public void addTrace(String user, String method){
        try {
            stadistics = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/StadisticsBean");
            log = InitialContext.doLookup("java:global/ProyectoAS2/ProyectoAS2-ejb/LogBean");
            stadistics.addComponentUsers(user);
            log.addFuntion("PayMethodFacade::" + method + "::" + user);
            stadistics.addComponent("PayMethodFacade");
        } catch (NamingException ex) {
            Logger.getLogger(ClientFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
