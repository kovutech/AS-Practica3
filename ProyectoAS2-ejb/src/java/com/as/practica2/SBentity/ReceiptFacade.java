/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.sbEntity;

import com.as.practica2.entity.Policy;
import com.as.practica2.entity.Receipt;
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
public class ReceiptFacade extends AbstractFacade<Receipt> {

    @PersistenceContext(unitName = "ProyectoAS2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceiptFacade() {
        super(Receipt.class);
    }

    public List<Receipt> ReceiptByCodPolicy(Policy policy) {
        List<Receipt> receipts = em.createNamedQuery("Receipt.findByCodPolicy")
                .setParameter("codPolicy", policy)
                .getResultList();
        if (receipts.size() > 0) {
            return receipts;
        }
        return new ArrayList<Receipt>();
    }

    //receiptFacade.addReceipt(policy,receiptState,request.getParameter("amount"),request.getParameter("identification"));
    public void addReceipt(Policy policy, ReceiptState receiptState, String date, String amount, String identification) {
        String query = "INSERT INTO Receipt VALUES(?,?,?,?,?,?)";
        em.createNativeQuery(query)
                .setParameter(1, null)
                .setParameter(2, policy.getIdPolicy())
                .setParameter(3, receiptState.getIdReceiptState())
                .setParameter(4, date)
                .setParameter(5, amount)
                .setParameter(6, identification)
                .executeUpdate();
    }

    public Receipt findByIdReceipt(int n) {
        List<Receipt> receipts = em.createNamedQuery("Receipt.findByIdReceipt")
                .setParameter("idReceipt", n)
                .getResultList();
        if (receipts.size() > 0) {
            return receipts.get(0);
        }
        return new Receipt();
    }

    public void chargedReceipt(Receipt receipt) {
        String query = "UPDATE Receipt SET cod_state = :cod_state WHERE id_receipt = :id_receipt";
        em.createQuery(query)
                .setParameter("cod_state", 4)
                .setParameter("id_receipt", receipt.getIdReceipt())
                .executeUpdate();

//        String query = "UPDATE Cart SET amount = :amount, price = :price WHERE id = :id AND name LIKE :name";
//        em.createQuery(query)
//                .setParameter("amount", amount + 1)
//                .setParameter("price", price + precioOne)
//                .setParameter("id", id)
//                .setParameter("name", name)
//                .executeUpdate();
    }
}
