package edu.upc.dsa.models;

public class Game {
    String id;
    String description;
    int numlevels;

    public Game(String id, String description, int numlevels) {
        this.id = id;
        this.description = description;
        this.numlevels = numlevels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumlevels() {
        return numlevels;
    }

    public void setNumlevels(int numlevels) {
        this.numlevels = numlevels;
    }
}
