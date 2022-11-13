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
        Assert.assertEquals(2, this.sm.numGames());
        sm.createGame("3333", "partida de tiros",4);
        Assert.assertEquals(3, this.sm.numGames());

    }
    @Test
    public void testStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{
        this.sm.startGame("2222","qwer" );
        Assert.assertEquals(1,sm.getUsersByPoints("2222").get(0).getStatus());
        Assert.assertEquals(1,sm.getUsersByPoints("2222").get(0).getLevel());
        Assert.assertEquals(50,sm.getUsersByPoints("2222").get(0).getPoints());
        Assert.assertEquals("2222",sm.getUsersByPoints("2222").get(0).getIdGame());
    }
    @Test
    public void testInvalidUserStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{

        Assert.assertThrows(InvalidUserException.class,()->this.sm.startGame("1111","tttt" ));
    }
    @Test
    public void testInvalidGameStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{
        Assert.assertThrows(InvalidGameException.class,()->this.sm.startGame("6666","qwer" ));
    }
    @Test
    public void testUserAlreadyGameStartGame() throws InvalidUserException, InvalidGameException, UserAlreadyGameException{
        Assert.assertThrows(UserAlreadyGameException.class,()->this.sm.startGame("1111","zxcv" ));
    }
    @Test
    public void testGetUserLevel() throws InvalidGameException {
        Assert.assertEquals(1,sm.getUsersByPoints("1111").get(0).getLevel());
    }
    @Test
    public void testGetPoints() throws InvalidUserException, UserNotInGameException, InvalidGameException {
        Assert.assertEquals(50,sm.getUsersByPoints("1111").get(0).getPoints());
    }
    @Test
    public void testUpgradeLevel() throws InvalidUserException, UserNotInGameException, InvalidGameException, UserAlreadyGameException {
        this.sm.startGame("1111","poiu" );
        sm.upgradeLevel("poiu",100,"22/07/2020");
        Assert.assertEquals(150,sm.getUsersByPoints("1111").get(0).getPoints());
        Assert.assertEquals(2,sm.getUsersByPoints("1111").get(0).getLevel());
    }
    @Test
    public void testEndGame() throws InvalidGameException, UserNotInGameException, InvalidUserException {
        sm.endGame("zxcv");
        Assert.assertEquals(0,sm.getUsersByPoints("1111").get(0).getStatus());
    }
    @Test
    public void testGetUsersByPoints() throws UserNotInGameException, InvalidUserException, InvalidGameException, UserAlreadyGameException {
        this.sm.startGame("1111","qwer" );
        this.sm.startGame("1111","poiu" );
        sm.upgradeLevel("zxcv",100,"22/07/2020");
        Assert.assertEquals("zxcv",sm.getUsersByPoints("1111").get(0).getIdUser());
        Assert.assertEquals("qwer",sm.getUsersByPoints("1111").get(1).getIdUser());
        Assert.assertEquals("poiu",sm.getUsersByPoints("1111").get(2).getIdUser());

    }
    @Test
    public void testGetGames() throws InvalidUserException {
        List<String> l = sm.getGames("zxcv");
        List<String> l2 = new ArrayList<>();
        l2.add("1111");
        Assert.assertEquals(l2,l);
    }
    @Test
    public void testGetActivities() throws InvalidGameException {
        List<Act> l = sm.getActivities("1111","zxcv");
        Assert.assertEquals(l.get(l.size()-1).getLevel(),sm.getUsersByPoints("1111").get(0).getLevel());

    }
}
