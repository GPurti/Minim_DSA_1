package edu.upc.dsa;

import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Obj;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ShopManagerImplTest {


    ShopManager sm;

    @Before
    public void setUp() throws UserAlreadyExistsException {
        sm= new ShopManagerImpl();
        Credentials credentials1 = new Credentials("guillempurti@gmail.com","1234");
        sm.addUser("Guillem","Purti","22/01/2012", credentials1);
        Credentials credentials2 = new Credentials("albaroma@gmail.com","1456");
        sm.addUser("Alba","Purti","15/03/2013", credentials2);
        Credentials credentials3 = new Credentials("marcmoran@gmail.com","1234");
        sm.addUser("Marc","Moran","04/10/2018", credentials3);

        sm.addObject("Ordinador","ordinador negre",20);
        sm.addObject("Cadira","de plastic blau",10);
        sm.addObject("Ratoli","color blanc",5);
    }

    @After
    public void tearDown() {
        this.sm = null;
    }

    @Test
    public void testAddUser() throws UserAlreadyExistsException {
        Assert.assertEquals(3, this.sm.numUsers());
        Credentials credentials4= new Credentials("victor@gmail.com","987");
        this.sm.addUser("Victor","Fernandez","09/11/2002", credentials4);
        Assert.assertEquals(4, this.sm.numUsers());
    }
    @Test
    public void testAddInvalidUser() throws UserAlreadyExistsException {
        Assert.assertEquals(3, this.sm.numUsers());
        Credentials credentials4= new Credentials("guillempurti@gmail.com","987");
        Assert.assertThrows(UserAlreadyExistsException.class,()->this.sm.addUser("Victor","Fernandez","09/11/2002", credentials4));

    }

    @Test
    public void testGetAlphabeticUserList() {
        List<User> users = this.sm.getAlphabeticUserList();
        Assert.assertEquals("Moran", users.get(0).getSurname());
        Assert.assertEquals("Alba", users.get(1).getName());
        Assert.assertEquals("Guillem", users.get(2).getName());

    }
    @Test
    public void testInvalidUserLogin() throws InvalidCredentialsException {
        Credentials credentials4= new Credentials("victor@gmail.com","987");
        Assert.assertThrows(InvalidCredentialsException.class,()->this.sm.userLogin(credentials4));
    }

    @Test
    public void testAddObject(){
        Assert.assertEquals(3, this.sm.numObject());
        sm.addObject("Bombeta","iluminacio clara",15);
        Assert.assertEquals(4, this.sm.numObject());
    }

    @Test
    public void testGetObjectList(){
        List<Obj> objects = sm.getObjectList();
        Assert.assertEquals(3, objects.size());
        Assert.assertEquals("Ordinador", objects.get(0).getName());
        Assert.assertEquals(20, objects.get(0).getCoins());
        Assert.assertEquals("Ratoli", objects.get(2).getName());
        Assert.assertEquals(5, objects.get(2).getCoins());

    }

    @Test
    public void testBuyObject() throws NotEnoughMoneyException, InvalidCredentialsException, UserAlreadyExistsException {
        List<User> userList= this.sm.getAlphabeticUserList();
        List<Obj> ob1 = sm.buyObject("Ordinador",  userList.get(0).getId());
        Assert.assertEquals("Ordinador",ob1.get(0).getName());
    }
    @Test
    public void testNotEnoughMoneyToBuyObject() throws NotEnoughMoneyException, InvalidCredentialsException, UserAlreadyExistsException {
        List<User> userList= this.sm.getAlphabeticUserList();
        userList.get(0).setSaldo(5);
        Assert.assertThrows(NotEnoughMoneyException.class,()->sm.buyObject("Ordinador",  userList.get(0).getId()));
    }
    @Test
    public void testBuyObjectWithInvalidCredentials() throws NotEnoughMoneyException, InvalidCredentialsException, UserAlreadyExistsException {
        List<User> userList= this.sm.getAlphabeticUserList();
        Assert.assertThrows(InvalidCredentialsException.class,()->sm.buyObject("Ordinador",  "adb123"));
    }


}
