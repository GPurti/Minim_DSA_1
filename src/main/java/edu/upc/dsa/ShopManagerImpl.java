package edu.upc.dsa;

import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Obj;
import edu.upc.dsa.models.User;

import java.util.*;
import org.apache.log4j.Logger;

public class ShopManagerImpl implements ShopManager {

    private static ShopManager instance;
    Map<String,User> users;
    List<Obj> objects;

    final static Logger logger = Logger.getLogger(ShopManagerImpl.class);

    public static ShopManager getInstance() {
        if (instance==null) instance = new ShopManagerImpl();
        return instance;
    }
    public ShopManagerImpl(){
        this.users = new HashMap<>();
        this.objects = new ArrayList<>();

    }
    @Override
    public void addUser(String name, String surname, String date, Credentials credentials) throws UserAlreadyExistsException {
        logger.info("new User ("+name+",  "+surname+", "+ date+", "+ credentials.getEmail()+", "+credentials.getPassword()+")");
        for (User u : this.users.values()) {
            if (Objects.equals(credentials.getEmail(), u.getCredentials().getEmail())) {
                logger.warn("This user already exists");
                throw new UserAlreadyExistsException();
            }
        }
        User u = new User(name,surname,date,credentials);
        logger.info("new User " + u);
        String id = u.getId();
        this.users.put(id,u);
        logger.info("new User added");
    }


    @Override
    public List<User> getAlphabeticUserList(){
        List<User> userss = new ArrayList<>(this.users.values());

        userss.sort((User u1, User u2)->{
            int value = u1.getSurname().compareToIgnoreCase(u2.getSurname());
            if (value==0){
                value = u1.getName().compareToIgnoreCase(u2.getName());
            }
            return value;
        });
        return userss;
    }

    @Override
    public void userLogin(Credentials credentials) throws InvalidCredentialsException {
        if (!equalCredentials(credentials)) {
            logger.warn("Credentials " + credentials.getEmail() + " and "+credentials.getPassword()+  " not found");
            throw new InvalidCredentialsException();
        }
    }
    public Boolean equalCredentials(Credentials credentials){
        for( User u:users.values()){
            if (Objects.equals(u.getCredentials().getEmail(), credentials.getEmail())&&Objects.equals(u.getCredentials().getPassword(), credentials.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addObject(String name, String description, int coins) {

        Obj object = new Obj(name, description, coins);
        logger.info("new Object " + object);
        this.objects.add(object);
        logger.info("new Object added");
    }

    @Override
    public List<Obj> getObjectList() {
        logger.info("getObjectList()");
        objects.sort((Obj o1, Obj o2)->(o2.getCoins()- o1.getCoins()));
        return objects;
    }

    @Override
    public List<Obj> buyObject(String name, String id) throws InvalidCredentialsException, NotEnoughMoneyException {
        User u = users.get(id);
        if (u==null) {
            logger.warn("Credentials not found");
            throw new InvalidCredentialsException();
        }
        int money = users.get(id).getSaldo();
        Obj o  = dameDinero(name);
        if (money < o.getCoins()){
            logger.warn(o.getCoins()+" is not enough money");
            throw new NotEnoughMoneyException();
        }
        else {
            users.get(id).addObject(o);
            return users.get(id).getBoughtobjects();
        }
    }
    public Obj dameDinero(String name){
        for (Obj o:objects){
            if(Objects.equals(o.getName(), name))
                return o;
        }
        return null;
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
    public int numObject() {
        return this.objects.size();
    }

}
