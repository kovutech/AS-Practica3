/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.Client;
import com.as.practica2.entity.Policy;
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
public class PolicyFacade extends AbstractFacade<Policy> {

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
        if (policy.size() > 0) {
            return policy;
        } else {
            policy = new ArrayList<Policy>();
            return policy;
        }
    }
}
