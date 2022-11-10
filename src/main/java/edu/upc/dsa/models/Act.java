package edu.upc.dsa.models;

public class Act {
    int level;
    int points;
    String date;

    public Act(int level, int points, String date) {
        this.level = level;
        this.points = points;
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
