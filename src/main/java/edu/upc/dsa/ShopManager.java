package edu.upc.dsa;

import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Obj;
import edu.upc.dsa.models.User;

import java.util.*;

public interface ShopManager {

    public void addUser(String name, String surname, String date, Credentials credentials) throws UserAlreadyExistsException;
    public List<User> getAlphabeticUserList();
    public void userLogin(Credentials credentials) throws InvalidCredentialsException;
    public void addObject(String name, String description, int coins);
    public List<Obj> getObjectList();
    public List<Obj> buyObject(String name, String id) throws InvalidCredentialsException, NotEnoughMoneyException;

    public int size();

    public int numUsers();



    public int numObject();
}
