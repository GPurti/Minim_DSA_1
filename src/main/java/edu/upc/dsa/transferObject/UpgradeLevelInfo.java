package edu.upc.dsa.transferObject;

public class UpgradeLevelInfo {
    String idUser;
    int points;
    String date;

    public UpgradeLevelInfo() {}
    public UpgradeLevelInfo(String idUser, int points, String date) {
        this();
        this.idUser = idUser;
        this.points = points;
        this.date = date;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
