/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "Receipt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receipt.findAll", query = "SELECT r FROM Receipt r")
    , @NamedQuery(name = "Receipt.findByIdReceipt", query = "SELECT r FROM Receipt r WHERE r.idReceipt = :idReceipt")
    , @NamedQuery(name = "Receipt.findByChargeDate", query = "SELECT r FROM Receipt r WHERE r.chargeDate = :chargeDate")
    , @NamedQuery(name = "Receipt.findByAmount", query = "SELECT r FROM Receipt r WHERE r.amount = :amount")})
public class Receipt implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "reference")
    private String reference;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_receipt")
    private Integer idReceipt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "chargeDate")
    private String chargeDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private float amount;
    @JoinColumn(name = "cod_policy", referencedColumnName = "id_policy")
    @ManyToOne(optional = false)
    private Policy codPolicy;
    @JoinColumn(name = "cod_state", referencedColumnName = "id_ReceiptState")
    @ManyToOne(optional = false)
    private ReceiptState codState;

    public Receipt() {
    }

    public Receipt(Integer idReceipt) {
        this.idReceipt = idReceipt;
    }

    public Receipt(Integer idReceipt, String chargeDate, float amount) {
        this.idReceipt = idReceipt;
        this.chargeDate = chargeDate;
        this.amount = amount;
    }

    public Integer getIdReceipt() {
        return idReceipt;
    }

    public void setIdReceipt(Integer idReceipt) {
        this.idReceipt = idReceipt;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Policy getCodPolicy() {
        return codPolicy;
    }

    public void setCodPolicy(Policy codPolicy) {
        this.codPolicy = codPolicy;
    }

    public ReceiptState getCodState() {
        return codState;
    }

    public void setCodState(ReceiptState codState) {
        this.codState = codState;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceipt != null ? idReceipt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipt)) {
            return false;
        }
        Receipt other = (Receipt) object;
        if ((this.idReceipt == null && other.idReceipt != null) || (this.idReceipt != null && !this.idReceipt.equals(other.idReceipt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.Receipt[ idReceipt=" + idReceipt + " ]";
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    
}
