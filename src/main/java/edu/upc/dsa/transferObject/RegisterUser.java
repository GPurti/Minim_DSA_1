package edu.upc.dsa.transferObject;

import edu.upc.dsa.models.Credentials;

public class RegisterUser {
    String name;
    String surname;
    String date;
    Credentials credentials;


    public RegisterUser(){}
    public RegisterUser(String name, String surname, String date, Credentials credentials) {
        this();
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.credentials=credentials;
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

}
