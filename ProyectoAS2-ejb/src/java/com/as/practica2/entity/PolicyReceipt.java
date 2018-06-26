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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "PolicyReceipt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolicyReceipt.findAll", query = "SELECT p FROM PolicyReceipt p")
    , @NamedQuery(name = "PolicyReceipt.findByIdpolicyReceipt", query = "SELECT p FROM PolicyReceipt p WHERE p.idpolicyReceipt = :idpolicyReceipt")})
public class PolicyReceipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_policyReceipt")
    private Integer idpolicyReceipt;
    @JoinColumn(name = "cod_policy", referencedColumnName = "id_policy")
    @ManyToOne(optional = false)
    private Policy codPolicy;
    @JoinColumn(name = "cod_receipt", referencedColumnName = "id_receipt")
    @ManyToOne(optional = false)
    private Receipt codReceipt;

    public PolicyReceipt() {
    }

    public PolicyReceipt(Integer idpolicyReceipt) {
        this.idpolicyReceipt = idpolicyReceipt;
    }

    public Integer getIdpolicyReceipt() {
        return idpolicyReceipt;
    }

    public void setIdpolicyReceipt(Integer idpolicyReceipt) {
        this.idpolicyReceipt = idpolicyReceipt;
    }

    public Policy getCodPolicy() {
        return codPolicy;
    }

    public void setCodPolicy(Policy codPolicy) {
        this.codPolicy = codPolicy;
    }

    public Receipt getCodReceipt() {
        return codReceipt;
    }

    public void setCodReceipt(Receipt codReceipt) {
        this.codReceipt = codReceipt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpolicyReceipt != null ? idpolicyReceipt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolicyReceipt)) {
            return false;
        }
        PolicyReceipt other = (PolicyReceipt) object;
        if ((this.idpolicyReceipt == null && other.idpolicyReceipt != null) || (this.idpolicyReceipt != null && !this.idpolicyReceipt.equals(other.idpolicyReceipt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.PolicyReceipt[ idpolicyReceipt=" + idpolicyReceipt + " ]";
    }
    
}
