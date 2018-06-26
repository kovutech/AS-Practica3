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
@Table(name = "ReceiptState")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReceiptState.findAll", query = "SELECT r FROM ReceiptState r")
    , @NamedQuery(name = "ReceiptState.findByIdReceiptState", query = "SELECT r FROM ReceiptState r WHERE r.idReceiptState = :idReceiptState")
    , @NamedQuery(name = "ReceiptState.findByName", query = "SELECT r FROM ReceiptState r WHERE r.name = :name")})
public class ReceiptState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ReceiptState")
    private Integer idReceiptState;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codState")
    private Collection<Receipt> receiptCollection;

    public ReceiptState() {
    }

    public ReceiptState(Integer idReceiptState) {
        this.idReceiptState = idReceiptState;
    }

    public ReceiptState(Integer idReceiptState, String name) {
        this.idReceiptState = idReceiptState;
        this.name = name;
    }

    public Integer getIdReceiptState() {
        return idReceiptState;
    }

    public void setIdReceiptState(Integer idReceiptState) {
        this.idReceiptState = idReceiptState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (idReceiptState != null ? idReceiptState.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceiptState)) {
            return false;
        }
        ReceiptState other = (ReceiptState) object;
        if ((this.idReceiptState == null && other.idReceiptState != null) || (this.idReceiptState != null && !this.idReceiptState.equals(other.idReceiptState))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.ReceiptState[ idReceiptState=" + idReceiptState + " ]";
    }
    
}
