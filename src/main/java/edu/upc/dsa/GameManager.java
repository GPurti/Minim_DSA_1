package edu.upc.dsa;

import edu.upc.dsa.models.*;
import edu.upc.dsa.transferObject.ActualLevelInfo;


import java.util.*;

public interface GameManager {

    public void createGame(String idGame, String description, int numLevels);
    public void startGame(String idGame, String idUser) throws InvalidUserException, InvalidGameException, UserAlreadyGameException;
    public ActualLevelInfo getUserLevel(String idUser ) throws UserNotInGameException, InvalidUserException;
    public Integer getPoints(String idUser) throws InvalidUserException, UserNotInGameException;
    public void upgradeLevel(String idUser, int points, String date) throws InvalidUserException, UserNotInGameException;
    public void endGame(String idUser) throws UserNotInGameException, InvalidUserException;
    public List<User> getUsersByPoints(String idGame) throws InvalidGameException;
    public List<String> getGames(String idUser) throws InvalidUserException;

    public List<Act> getActivities(String idGame, String idUser);


    public void addUser(String id);

    public int numUsers();
    public int numGames();


}
