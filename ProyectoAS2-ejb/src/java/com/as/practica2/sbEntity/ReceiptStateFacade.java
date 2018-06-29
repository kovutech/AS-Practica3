/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.Policy;
import com.as.practica2.entity.ReceiptState;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jorge
 */
@Stateless
public class ReceiptStateFacade extends AbstractFacade<ReceiptState> {

    @PersistenceContext(unitName = "ProyectoAS2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceiptStateFacade() {
        super(ReceiptState.class);
    }

    public ReceiptState findByName(String s) {
        List<ReceiptState> receiptState = em.createNamedQuery("ReceiptState.findByName")
                .setParameter("name", s)
                .getResultList();
        if (receiptState.size() > 0) {
            return receiptState.get(0);
        }
        return null;
    }
}