package edu.upc.dsa.transferObject;

public class PointsInfo {
    int points;

    public PointsInfo() {}
    public PointsInfo(int points) {
        this();
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
