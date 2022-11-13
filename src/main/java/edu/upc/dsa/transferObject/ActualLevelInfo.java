package edu.upc.dsa.transferObject;

public class ActualLevelInfo {
    int level;
    String idGame;

    public ActualLevelInfo() {}
    public ActualLevelInfo(int level, String idGame) {
        this();
        this.level = level;
        this.idGame = idGame;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }
}
