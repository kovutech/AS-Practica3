/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.PayMethod;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jorge
 */
@Stateless
public class PayMethodFacade extends AbstractFacade<PayMethod> {

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
    
}
