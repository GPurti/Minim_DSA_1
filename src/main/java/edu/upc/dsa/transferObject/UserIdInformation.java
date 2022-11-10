package edu.upc.dsa.transferObject;

public class UserIdInformation {
    String name;
    String surname;
    String date;
    String id;


    public UserIdInformation(){}
    public UserIdInformation(String name, String surname, String date,String id) {
        this();
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.id=id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
