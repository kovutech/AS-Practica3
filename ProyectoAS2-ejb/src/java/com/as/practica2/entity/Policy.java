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
    , @NamedQuery(name = "Policy.findByAmount", query = "SELECT p FROM Policy p WHERE p.amount = :amount")})
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
    @JoinColumn(name = "cod_payMethod", referencedColumnName = "id_payMethod")
    @ManyToOne(optional = false)
    private PayMethod codpayMethod;
    @JoinColumn(name = "cod_product", referencedColumnName = "id_product")
    @ManyToOne(optional = false)
    private Products codProduct;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPolicy")
    private Collection<ClientPolicy> clientPolicyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPolicy")
    private Collection<PolicyReceipt> policyReceiptCollection;

    public Policy() {
    }

    public Policy(Integer idPolicy) {
        this.idPolicy = idPolicy;
    }

    public Policy(Integer idPolicy, String fromDate, String toDate, float amount) {
        this.idPolicy = idPolicy;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.amount = amount;
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

    public PayMethod getCodpayMethod() {
        return codpayMethod;
    }

    public void setCodpayMethod(PayMethod codpayMethod) {
        this.codpayMethod = codpayMethod;
    }

    public Products getCodProduct() {
        return codProduct;
    }

    public void setCodProduct(Products codProduct) {
        this.codProduct = codProduct;
    }

    @XmlTransient
    public Collection<ClientPolicy> getClientPolicyCollection() {
        return clientPolicyCollection;
    }

    public void setClientPolicyCollection(Collection<ClientPolicy> clientPolicyCollection) {
        this.clientPolicyCollection = clientPolicyCollection;
    }

    @XmlTransient
    public Collection<PolicyReceipt> getPolicyReceiptCollection() {
        return policyReceiptCollection;
    }

    public void setPolicyReceiptCollection(Collection<PolicyReceipt> policyReceiptCollection) {
        this.policyReceiptCollection = policyReceiptCollection;
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
