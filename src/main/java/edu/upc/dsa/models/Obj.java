package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Obj {
    String name;
    String description;
    int coins;
    String id;

    public Obj() {this.id = RandomUtils.getId();}
    public Obj(String name, String description, int coins) {
        this();
        this.name = name;
        this.description = description;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
