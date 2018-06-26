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
import javax.persistence.Lob;
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
@Table(name = "Client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByIdClient", query = "SELECT c FROM Client c WHERE c.idClient = :idClient")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_client")
    private Integer idClient;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "surName")
    private String surName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "telephone")
    private String telephone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codClient")
    private Collection<ClientPolicy> clientPolicyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codClient")
    private Collection<UserClient> userClientCollection;

    public Client() {
    }

    public Client(Integer idClient) {
        this.idClient = idClient;
    }

    public Client(Integer idClient, String name, String surName, String telephone) {
        this.idClient = idClient;
        this.name = name;
        this.surName = surName;
        this.telephone = telephone;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @XmlTransient
    public Collection<ClientPolicy> getClientPolicyCollection() {
        return clientPolicyCollection;
    }

    public void setClientPolicyCollection(Collection<ClientPolicy> clientPolicyCollection) {
        this.clientPolicyCollection = clientPolicyCollection;
    }

    @XmlTransient
    public Collection<UserClient> getUserClientCollection() {
        return userClientCollection;
    }

    public void setUserClientCollection(Collection<UserClient> userClientCollection) {
        this.userClientCollection = userClientCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClient != null ? idClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idClient == null && other.idClient != null) || (this.idClient != null && !this.idClient.equals(other.idClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.Client[ idClient=" + idClient + " ]";
    }
    
}
