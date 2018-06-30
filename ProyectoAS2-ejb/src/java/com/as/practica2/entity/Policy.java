/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.practica2.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "Policy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Policy.findAll", query = "SELECT p FROM Policy p")
    , @NamedQuery(name = "Policy.findByIdPolicy", query = "SELECT p FROM Policy p WHERE p.idPolicy = :idPolicy")
    , @NamedQuery(name = "Policy.findByFromDate", query = "SELECT p FROM Policy p WHERE p.fromDate = :fromDate")
    , @NamedQuery(name = "Policy.findByToDate", query = "SELECT p FROM Policy p WHERE p.toDate = :toDate")
    , @NamedQuery(name = "Policy.findByAmount", query = "SELECT p FROM Policy p WHERE p.amount = :amount")
    , @NamedQuery(name = "Policy.findByIdentification", query = "SELECT p FROM Policy p WHERE p.identification = :identification")
    , @NamedQuery(name = "Policy.findByCodClient", query = "SELECT p FROM Policy p WHERE p.codClient = :codClient")})
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_policy")
    private Integer idPolicy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "fromDate")
    private String fromDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "toDate")
    private String toDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private float amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "identification")
    private String identification;
    @JoinColumn(name = "cod_product", referencedColumnName = "id_product")
    @ManyToOne(optional = false)
    private Products codProduct;
    @JoinColumn(name = "cod_client", referencedColumnName = "id_client")
    @ManyToOne(optional = false)
    private Client codClient;
    @JoinColumn(name = "cod_payMethod", referencedColumnName = "id_payMethod")
    @ManyToOne(optional = false)
    private PayMethod codpayMethod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPolicy")
    private Collection<Receipt> receiptCollection;

    public Policy() {
    }

    public Policy(Integer idPolicy) {
        this.idPolicy = idPolicy;
    }

    public Policy(Integer idPolicy, Client codClient, Products codProduct, PayMethod payMethod, String fromDate, String toDate, float amount, String identification) {
        this.idPolicy = idPolicy;
        this.codClient = codClient;
        this.codProduct = codProduct;
        this.codpayMethod = payMethod;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.amount = amount;
        this.identification = identification;
    }

    public Integer getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(Integer idPolicy) {
        this.idPolicy = idPolicy;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Products getCodProduct() {
        return codProduct;
    }

    public void setCodProduct(Products codProduct) {
        this.codProduct = codProduct;
    }

    public Client getCodClient() {
        return codClient;
    }

    public void setCodClient(Client codClient) {
        this.codClient = codClient;
    }

    public PayMethod getCodpayMethod() {
        return codpayMethod;
    }

    public void setCodpayMethod(PayMethod codpayMethod) {
        this.codpayMethod = codpayMethod;
    }

    @XmlTransient
    public Collection<Receipt> getReceiptCollection() {
        return receiptCollection;
    }

    public void setReceiptCollection(Collection<Receipt> receiptCollection) {
        this.receiptCollection = receiptCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPolicy != null ? idPolicy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Policy)) {
            return false;
        }
        Policy other = (Policy) object;
        if ((this.idPolicy == null && other.idPolicy != null) || (this.idPolicy != null && !this.idPolicy.equals(other.idPolicy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.Policy[ idPolicy=" + idPolicy + " ]";
    }

}
