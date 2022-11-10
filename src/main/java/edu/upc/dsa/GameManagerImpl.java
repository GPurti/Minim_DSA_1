package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.*;

//import jdk.internal.net.http.common.Pair;
import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {

    private static GameManager instance;
    Map<String,User> users;
    List<Game> games;
    List<Act> activities;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }
    public GameManagerImpl(){
        this.users = new HashMap<>();
        this.games = new ArrayList<>();
        this.activities = new ArrayList<>();

    }

    @Override
    public void createGame(String id, String description, int numLevels) {
        logger.info("new Game ( "+id+",  "+description+", "+ numLevels+" )");
        Game game = new Game(id,description,numLevels);
        this.games.add(game);
        logger.info("new Game added");
    }

    @Override
    public void startGame(String gameId, String userId) throws InvalidUserException, InvalidGameException, UserAlreadyGameException {
        User u = users.get(userId);
        if (u==null) {
            logger.warn("User not found");
            throw new InvalidUserException();
        }
        if (findGame(gameId)==null){
            logger.warn("Game not found");
            throw new InvalidGameException();
        }
        if(u.getStatus()!=0){
            logger.warn("The user is already in game");
            throw new UserAlreadyGameException();
        }
        u.setStatus(1);
        Act a = new Act(1,50,"today");
        u.addGame(gameId,a);
        u.setPoints(50);
        u.setLevel(1);
    }

    @Override
    public void addUser(String id){
        logger.info("new User ( "+id+" )");
        User u = new User(id);
        this.users.put(id,u);
        logger.info("new User added");
    }

    public Game findGame(String gameId){
        for(Game g:games){
            if (g.getId()==gameId)
                return g;
        }
        return null;
    }

/*
    @Override
    public Pair<Integer, Game> getUserLevel(String id) {
        User u = users.get(id);
        u.getGameActivities();
        return new Pair<Integer, Game> (u.getLevel(), findGame(u.getIdGame()));
    }*/

    @Override
    public Integer getPoints(String id) throws InvalidUserException, UserNotInGameException {
        User u = userNotFoundNotGame(id);
        return u.getPoints();
    }

    @Override
    public void upgradeLevel(String id, int points, String date) throws InvalidUserException, UserNotInGameException {
        User u = userNotFoundNotGame(id);
        u.setLevel(u.getLevel()+1);
        if (u.getLevel()>=findGame(u.getIdGame()).getNumlevels())
        {
            u.setPoints(u.getPoints()+100);
            u.setStatus(0);
        }
        else {
            u.setPoints(u.getPoints() + points);
        }
        Map<String,List<Act>> actList = u.getGameActivities();
        List<Act> activities = actList.get(u.getIdGame());
        Act a = new Act(u.getLevel(), u.getPoints(),date);
        activities.add(a);

    }

    @Override
    public void endGame(String id) {
        User u = users.get(id);
        u.setStatus(0);

    }

    public User userNotFoundNotGame(String id) throws InvalidUserException, UserNotInGameException {
        User u = users.get(id);
        if (u==null) {
            logger.warn("User not found");
            throw new InvalidUserException();
        }
        if(u.getStatus()!=0){
            logger.warn("The user is not in game");
            throw new UserNotInGameException();
        }
        return u;
    }
    @Override
    public List<User> getUsersByPoints() {
        logger.info("getUserByPoints()");
        List<User> userss = new ArrayList<>(this.users.values());
        userss.sort((User u1, User u2)->(u1.getPoints()- u2.getPoints()));
        return userss;
    }

    @Override
    public List<String> getGames(String id) {
        logger.info("getGames()");
        User u = users.get(id);
        List<String> l = new ArrayList<String>(u.getGameActivities().keySet());
        logger.info("return games ids");
        return l;
    }


    @Override
    public List<Act> getActivities(String id, String userId) {
        Map<String,List<Act>> actList = users.get(userId).getGameActivities();
        List<Act> activities = actList.get(id);
        return activities;
    }

    @Override
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public int numUsers() {
        return this.users.size();
    }
    @Override
    public int numGames() {
        return this.games.size();
    }



}
