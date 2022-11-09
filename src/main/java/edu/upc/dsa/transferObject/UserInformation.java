package edu.upc.dsa.transferObject;

import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Obj;
import edu.upc.dsa.util.RandomUtils;

import java.util.*;

public class UserInformation {
    String name;
    String surname;
    String date;
    Credentials credentials;


    public UserInformation(){}
    public UserInformation(String name, String surname, String date, Credentials credentials) {
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
