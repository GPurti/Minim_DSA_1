package edu.upc.dsa;

import edu.upc.dsa.models.*;


import java.util.*;

public interface GameManager {

    public void createGame(String id, String description, int numLevels);
    public void startGame(String gameId, String userId) throws InvalidUserException, InvalidGameException, UserAlreadyGameException;
    //public Pair<Integer, Game> getUserLevel(String id );
    public Integer getPoints(String id) throws InvalidUserException, UserNotInGameException;
    public void upgradeLevel(String id, int points, String date) throws InvalidUserException, UserNotInGameException;
    public void endGame(String gameId);
    public List<User> getUsersByPoints();
    public List<String> getGames(String id);

    public List<Act> getActivities(String id, String userId);


    public void addUser(String id);
    public int size();

    public int numUsers();
    public int numGames();


}
