package edu.upc.dsa;

import edu.upc.dsa.models.*;
//import jdk.internal.net.http.common.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameManagerImplTest {


    GameManager sm;

    @Before
    public void setUp() throws UserAlreadyExistsException, UserAlreadyGameException, InvalidGameException, InvalidUserException {
        sm= new GameManagerImpl();
        sm.addUser("qwer");
        sm.addUser("zxcv");
        sm.addUser("poiu");
        sm.createGame("1111", "partida de rol",3);
        this.sm.startGame("1111","zxcv" );
        sm.createGame("2222", "partida de tiros",3);
    }

    @After
    public void tearDown() {
        this.sm = null;
    }

    @Test
    public void testCreateGame(){
        Assert.assertEquals(1, this.sm.numGames());
        sm.createGame("2222", "partida de tiros",4);
        Assert.assertEquals(2, this.sm.numGames());

    }
    @Test
    public void testStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{
        Assert.assertEquals(0,sm.getUsersByPoints().get(0).getStatus());
        Assert.assertEquals(0,sm.getUsersByPoints().get(1).getStatus());
        this.sm.startGame("2222","qwer" );
        Assert.assertEquals(1,sm.getUsersByPoints().get(1).getStatus());
        Assert.assertEquals(1,sm.getUsersByPoints().get(1).getLevel());
    }
    public void testInvalidUserStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{

        Assert.assertThrows(InvalidUserException.class,()->this.sm.startGame("1111","tttt" ));
    }
    public void testInvalidGameStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{
        Assert.assertThrows(InvalidGameException.class,()->this.sm.startGame("6666","qwer" ));
    }
    public void testUseralreadyGameStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{
        Assert.assertThrows(UserAlreadyGameException.class,()->this.sm.startGame("1111","zxcv" ));
    }
    public void testGetUserLevel(){
        Assert.assertEquals(1,sm.getUsersByPoints().get(0).getLevel());
    }
    public void testGetPoints() throws InvalidUserException, UserNotInGameException{
        Assert.assertEquals(50,sm.getUsersByPoints().get(0).getPoints());
    }
    public void testUpgradeLevel(String id, int points, String date) throws InvalidUserException, UserNotInGameException{
        sm.upgradeLevel("poiu",100,"22/07/2020");
        Assert.assertEquals(150,sm.getUsersByPoints().get(0).getPoints());
        Assert.assertEquals(2,sm.getUsersByPoints().get(0).getLevel());
    }
    public void testEndGame(String gameId){
        sm.endGame("1111");
        Assert.assertEquals(0,sm.getUsersByPoints().get(1).getStatus());
    }
    public void testGetUsersByPoints() throws UserNotInGameException, InvalidUserException {
        sm.upgradeLevel("poiu",100,"22/07/2020");
        Assert.assertEquals("poiu",sm.getUsersByPoints().get(0).getIdUser());
        Assert.assertEquals("qwer",sm.getUsersByPoints().get(1).getIdUser());
        Assert.assertEquals("zxcv",sm.getUsersByPoints().get(1).getIdUser());

    }
    public void testGetGames(){
        List<String> l = sm.getGames("zxcv");
        List<String> l2 = new ArrayList<>();
        l2.add("1111");
        Assert.assertEquals(l2,l);
    }

    public void testGetActivities(String id, String userId){
        List<Act> l = sm.getActivities("1111","zxcv");
        Act a = new Act(1,50,"");
        List<Act> l2 = new ArrayList<>();
        l2.add(a);
        Assert.assertEquals(l2,l);
    }
}
