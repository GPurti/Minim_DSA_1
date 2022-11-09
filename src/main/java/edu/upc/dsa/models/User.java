package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.*;


public class User {
    String name;
    String surname;
    String date;
    Credentials credentials;
    String id;
    int saldo;
    List<Obj> boughtobjects;


    public User(){this.id = RandomUtils.getId();}
    public User(String name, String surname, String date, Credentials credentials) {
        this();
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.credentials=credentials;
        this.saldo = 50;
        this.boughtobjects = new ArrayList<>();
    }

    public void addObject(Obj object){
        boughtobjects.add(object);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public List<Obj> getBoughtobjects() {
        return boughtobjects;
    }

    public void setBoughtobjects(List<Obj> boughtobjects) {
        this.boughtobjects = boughtobjects;
    }
}
