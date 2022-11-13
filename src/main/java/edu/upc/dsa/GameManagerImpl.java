package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.*;

//import jdk.internal.net.http.common.Pair;
import edu.upc.dsa.transferObject.ActualLevelInfo;
import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {

    private static GameManager instance;
    Map<String,User> users;
    List<Game> games;


    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }
    public GameManagerImpl(){
        this.users = new HashMap<>();
        this.games = new ArrayList<>();
    }

    @Override
    public void createGame(String idGame, String description, int numLevels) {
        logger.info("new Game ( "+idGame+",  "+description+", "+ numLevels+" )");
        Game game = new Game(idGame,description,numLevels);
        this.games.add(game);
        logger.info("new Game added");
    }

    @Override
    public void startGame(String idGame, String userId) throws InvalidUserException, InvalidGameException, UserAlreadyGameException {
        logger.info("start Game ( "+idGame+",  "+userId+" )");
        User u = users.get(userId);
        if (u==null) {
            logger.warn("User not found");
            throw new InvalidUserException();
        }
        if (findGame(idGame)==null){
            logger.warn("Game not found");
            throw new InvalidGameException();
        }
        if(u.getStatus()!=0){
            logger.warn("The user is already in game");
            throw new UserAlreadyGameException();
        }
        u.setStatus(1);
        Act a = new Act(1,50,"today");
        u.addGame(idGame,a);
        u.setPoints(50);
        u.setLevel(1);
        u.setIdGame(idGame);
        logger.info("Game started");
    }

    @Override
    public void addUser(String idUser){
        logger.info("new User ( "+idUser+" )");
        User u = new User(idUser);
        this.users.put(idUser,u);
        logger.info("new User added");
    }

    public Game findGame(String idGame){
        for(Game g:games){
            if (Objects.equals(g.getId(), idGame))
                return g;
        }
        return null;
    }

    @Override
    public ActualLevelInfo getUserLevel(String idUser) throws UserNotInGameException, InvalidUserException {
        logger.info("getUserLevel( "+idUser+" )");
        User u = userNotFoundNotGame(idUser);
        logger.info("list of user level received");
        return new ActualLevelInfo(u.getLevel(),u.getIdGame());
    }

    @Override
    public Integer getPoints(String idUser) throws InvalidUserException, UserNotInGameException {
        logger.info("getPoints( "+idUser+" )");
        User u = userNotFoundNotGame(idUser);
        logger.info("user points received");
        return u.getPoints();
    }

    @Override
    public void upgradeLevel(String idUser, int points, String date) throws InvalidUserException, UserNotInGameException {
        logger.info("upgradeLevel( "+idUser+",  "+points+", "+ date+" )");
        User u = userNotFoundNotGame(idUser);
        Game game = findGame(u.getIdGame());
        u.modifyLevel(points, date, game);
        logger.info("user level upgraded");
    }

    @Override
    public void endGame(String idUser) throws UserNotInGameException, InvalidUserException {
        logger.info("endGame( "+idUser+" )");
        User u = userNotFoundNotGame(idUser);
        u.setStatus(0);
        logger.info("Game ended");
    }

    @Override
    public List<User> getUsersByPoints(String idGame) throws InvalidGameException {
        logger.info("getUserByPoints("+idGame+" )");
        if (findGame(idGame)==null){
            logger.warn("Game not found");
            throw new InvalidGameException();
        }
        List<User> usersList = new ArrayList<>(this.users.values());
        List<User> userOfGame= getListGameUsers(usersList,idGame);

        userOfGame.sort((User u2, User u1)->(u1.getPointsOfGame(idGame)- u2.getPointsOfGame(idGame)));
        logger.info("list of users ordered by points");
        return userOfGame;
    }

    public List<User> getListGameUsers(List<User> users, String idGame){
        List<User> userList = new ArrayList<>();
        for(User u: users){
            if(u.getGameActivities().get(idGame)!=null)
            {
                userList.add(u);
            }
        }
        return userList;
    }

    @Override
    public List<String> getGames(String idUser) throws InvalidUserException {
        logger.info("getGames("+idUser+" )");
        User u = users.get(idUser);
        if (u==null) {
            logger.warn("User not found");
            throw new InvalidUserException();
        }
        List<String> l = new ArrayList<String>(u.getGameActivities().keySet());
        logger.info("return games ids");
        return l;
    }


    @Override
    public List<Act> getActivities(String idGame, String idUser) {
        logger.info("upgradeLevel( "+idGame+",  "+idUser+" )");
        Map<String,List<Act>> actList = users.get(idUser).getGameActivities();
        List<Act> activities = actList.get(idGame);
        logger.info("return list of activities");
        return activities;
    }

    @Override
    public int numUsers() {
        int ret = this.users.size();
        logger.info("size of users " + ret);
        return ret;
    }

    @Override
    public int numGames() {
        int ret = this.games.size();
        logger.info("size of games " + ret);
        return ret;
    }

    public User userNotFoundNotGame(String idUser) throws InvalidUserException, UserNotInGameException {
        User u = users.get(idUser);
        if (u==null) {
            logger.warn("User not found");
            throw new InvalidUserException();
        }
        if(u.getStatus()==0){
            logger.warn("The user is not in game");
            throw new UserNotInGameException();
        }
        return u;
    }
}
