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
@Table(name = "PayMethod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayMethod.findAll", query = "SELECT p FROM PayMethod p")
    , @NamedQuery(name = "PayMethod.findByIdPayMethod", query = "SELECT p FROM PayMethod p WHERE p.idpayMethod = :idpayMethod")
    , @NamedQuery(name = "PayMethod.findByName", query = "SELECT p FROM PayMethod p WHERE p.name = :name")})
public class PayMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_payMethod")
    private Integer idpayMethod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codpayMethod")
    private Collection<Policy> policyCollection;

    public PayMethod() {
    }

    public PayMethod(Integer idpayMethod) {
        this.idpayMethod = idpayMethod;
    }

    public PayMethod(Integer idpayMethod, String name) {
        this.idpayMethod = idpayMethod;
        this.name = name;
    }

    public Integer getIdpayMethod() {
        return idpayMethod;
    }

    public void setIdpayMethod(Integer idpayMethod) {
        this.idpayMethod = idpayMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Policy> getPolicyCollection() {
        return policyCollection;
    }

    public void setPolicyCollection(Collection<Policy> policyCollection) {
        this.policyCollection = policyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpayMethod != null ? idpayMethod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayMethod)) {
            return false;
        }
        PayMethod other = (PayMethod) object;
        if ((this.idpayMethod == null && other.idpayMethod != null) || (this.idpayMethod != null && !this.idpayMethod.equals(other.idpayMethod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.PayMethod[ idpayMethod=" + idpayMethod + " ]";
    }
    
}
