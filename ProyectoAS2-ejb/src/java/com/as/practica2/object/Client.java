package com.as.practica2.object;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jorge
 */
public class Client {

    private String id;
    private String name;
    private String surame;
    private String telephone;

    public Client(String id, String name, String surame, String telephone) {
        this.id = id;
        this.name = name;
        this.surame = surame;
        this.telephone = telephone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurame() {
        return surame;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurame(String surame) {
        this.surame = surame;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
