package edu.upc.dsa.transferObject;

public class UserIdInformation {
    String nameId;

    int points;
    String gameid;


    public UserIdInformation(){}

    public UserIdInformation(String nameId, int points, String gameid) {
        this.nameId = nameId;
        this.points = points;
        this.gameid = gameid;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }
}
