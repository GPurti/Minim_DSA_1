package edu.upc.dsa.transferObject;

import edu.upc.dsa.util.RandomUtils;

public class ObjectInformation {
    String name;
    String description;
    int coins;


    public ObjectInformation() {}
    public ObjectInformation(String name, String description, int coins) {
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

}
