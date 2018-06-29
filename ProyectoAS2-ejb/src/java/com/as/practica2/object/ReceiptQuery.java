package com.as.practica2.object;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jorge
 */
public class ReceiptQuery {

    String clientName;
    String policyIdentification;
    String productsName;
    String receiptReference;
    String receiptAmount;
    String receiptStateName;
    String receiptChargeDate;

    public ReceiptQuery(String clientName, String policyIdentification, String productsName, String receiptReference, String receiptAmount, String receiptStateName, String receiptChargeDate) {
        this.clientName = clientName;
        this.policyIdentification = policyIdentification;
        this.productsName = productsName;
        this.receiptReference = receiptReference;
        this.receiptAmount = receiptAmount;
        this.receiptStateName = receiptStateName;
        this.receiptChargeDate = receiptChargeDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPolicyIdentification() {
        return policyIdentification;
    }

    public void setPolicyIdentification(String policyIdentification) {
        this.policyIdentification = policyIdentification;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getReceiptReference() {
        return receiptReference;
    }

    public void setReceiptReference(String receiptReference) {
        this.receiptReference = receiptReference;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getReceiptStateName() {
        return receiptStateName;
    }

    public void setReceiptStateName(String receiptStateName) {
        this.receiptStateName = receiptStateName;
    }

    public String getReceiptChargeDate() {
        return receiptChargeDate;
    }

    public void setReceiptChargeDate(String receiptChargeDate) {
        this.receiptChargeDate = receiptChargeDate;
    }
}
