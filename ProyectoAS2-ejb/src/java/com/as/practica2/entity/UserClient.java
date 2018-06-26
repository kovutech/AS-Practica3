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
@Table(name = "UserClient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserClient.findAll", query = "SELECT u FROM UserClient u")
    , @NamedQuery(name = "UserClient.findByIduserClient", query = "SELECT u FROM UserClient u WHERE u.iduserClient = :iduserClient")})
public class UserClient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_userClient")
    private Integer iduserClient;
    @JoinColumn(name = "cod_client", referencedColumnName = "id_client")
    @ManyToOne(optional = false)
    private Client codClient;
    @JoinColumn(name = "cod_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User codUser;

    public UserClient() {
    }

    public UserClient(Integer iduserClient) {
        this.iduserClient = iduserClient;
    }

    public Integer getIduserClient() {
        return iduserClient;
    }

    public void setIduserClient(Integer iduserClient) {
        this.iduserClient = iduserClient;
    }

    public Client getCodClient() {
        return codClient;
    }

    public void setCodClient(Client codClient) {
        this.codClient = codClient;
    }

    public User getCodUser() {
        return codUser;
    }

    public void setCodUser(User codUser) {
        this.codUser = codUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduserClient != null ? iduserClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserClient)) {
            return false;
        }
        UserClient other = (UserClient) object;
        if ((this.iduserClient == null && other.iduserClient != null) || (this.iduserClient != null && !this.iduserClient.equals(other.iduserClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.practica2.entity.UserClient[ iduserClient=" + iduserClient + " ]";
    }
    
}
