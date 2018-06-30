/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.object;

/**
 *
 * @author Jorge
 */
public class ReceiptA {

    String reference;
    String date;
    String amount;
    boolean paid;

    public ReceiptA(String reference, String date, String amount, boolean paid) {
        this.reference = reference;
        this.date = date;
        this.amount = amount;
        this.paid = paid;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
