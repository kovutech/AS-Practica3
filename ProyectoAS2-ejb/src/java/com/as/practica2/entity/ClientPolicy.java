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
@Table(name = "ClientPolicy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientPolicy.findAll", query = "SELECT c FROM ClientPolicy c")
    , @NamedQuery(name = "ClientPolicy.findByIdclientPolicy", query = "SELECT c FROM ClientPolicy c WHERE c.idclientPolicy = :idclientPolicy")})
public class ClientPolicy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_clientPolicy")
    private Integer idclientPolicy;
    @JoinColumn(name = "cod_client", referencedColumnName = "id_client")
    @ManyToOne(optional = false)
    private Client codClient;
    @JoinColumn(name = "cod_policy", referencedColumnName = "id_policy")
    @ManyToOne(optional = false)
    private Policy codPolicy;

    public ClientPolicy() {
    }

    public ClientPolicy(Integer idclientPolicy) {
        this.idclientPolicy = idclientPolicy;
    }

    public Integer getIdclientPolicy() {
        return idclientPolicy;
    }

    public void setIdclientPolicy(Integer idclientPolicy) {
        this.idclientPolicy = idclientPolicy;
    }

    public Client getCodClient() {
        return codClient;
    }

    public void setCodClient(Client codClient) {
        this.codClient = codClient;
    }

    public Policy getCodPolicy() {
        return codPolicy;
    }

    public void setCodPolicy(Policy codPolicy) {
        this.codPolicy = codPolicy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclientPolicy != null ? idclientPolicy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientPolicy)) {
            return false;
        }
        ClientPolicy other = (ClientPolicy) object;
        if ((this.idclientPolicy == null && other.idclientPolicy != null) || (this.idclientPolicy != null && !this.idclientPolicy.equals(other.idclientPolicy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.ClientPolicy[ idclientPolicy=" + idclientPolicy + " ]";
    }
    
}
