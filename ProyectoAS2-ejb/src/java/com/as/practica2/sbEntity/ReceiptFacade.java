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

    //JPQL
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
    public void addReceipt(Policy policy, ReceiptState receiptState, String date, String amount, String identification, String client, String policyType, String nPolicy) {
        String query = "INSERT INTO Receipt VALUES(?,?,?,?,?,?,?,?,?)";
        em.createNativeQuery(query)
                .setParameter(1, null)
                .setParameter(2, policy.getIdPolicy())
                .setParameter(3, receiptState.getIdReceiptState())
                .setParameter(4, date)
                .setParameter(5, amount)
                .setParameter(6, identification)
                .setParameter(7, client)
                .setParameter(8, policyType)
                .setParameter(9, nPolicy)
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

    public void chargedReceipt(Receipt receipt, ReceiptState receiptState) {
        String query = "UPDATE Receipt SET codState = :codState WHERE idReceipt = :idReceipt";
        em.createQuery(query)
                .setParameter("codState", receiptState)
                .setParameter("idReceipt", receipt.getIdReceipt())
                .executeUpdate();
    }

    public void deleteReceipt(Receipt receipt) {
        String query = "DELETE FROM Receipt where idReceipt= :idReceipt";
        em.createQuery(query)
                .setParameter("idReceipt", receipt.getIdReceipt())
                .executeUpdate();
    }

    public List<Receipt> searchReceiptsJPQL(String client, String type, String order, int page) {
        if (page < 1) {
            page = 1;
        }
        String query = "SELECT r FROM Receipt r WHERE r.client LIKE :client and r.tipoPoliza LIKE :tipoPoliza order by r.client " + order;

        if (client.equals("")) {
            client = "%";
        }
        if (type.equals("")) {
            type = "%";
        }

        List<Receipt> results = em.createQuery(query)
                .setParameter("client", client)
                .setParameter("tipoPoliza", type)
                .setFirstResult((page - 1) * 5)
                .setMaxResults(5)
                .getResultList();
        if (results.size() > 0) {
            return results;
        } else {
            return new ArrayList<Receipt>();
        }
    }
}
