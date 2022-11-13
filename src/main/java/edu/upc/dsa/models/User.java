package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.*;


public class User {

    String idUser;
    String idGame;
    int level;
    int points;
    Map<String,List<Act>> gameActivities;
    int status;


    public User(){}
    public User(String idUser) {
        this();
        this.idUser=idUser;
        this.gameActivities = new HashMap<>();
        this.status=0;
        this.points=0;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public Map<String, List<Act>> getGameActivities() {
        return gameActivities;
    }

    public void setGameActivities(Map<String, List<Act>> gameActivities) {
        this.gameActivities = gameActivities;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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


    public void addGame(String idGame, Act a){
        List<Act> l2 = new ArrayList<>();
        l2.add(a);
        gameActivities.put(idGame,l2);
    }

    public void modifyLevel( int points, String date, Game game){
        setLevel(getLevel()+1);
        if (getLevel()>=game.getNumlevels())
        {
            setPoints(getPoints()+100);
            setStatus(0);
        }
        else {
            setPoints(getPoints() + points);
        }

        List<Act> activities = gameActivities.get(getIdGame());
        Act a = new Act(getLevel(), getPoints(),date);
        activities.add(a);
    }
    public Integer getPointsOfGame(String idGame){
        return gameActivities.get(idGame).get(gameActivities.get(idGame).size()-1).getPoints();
    }


}
