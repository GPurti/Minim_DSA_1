package edu.upc.dsa.services;

import edu.upc.dsa.*;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Obj;
import edu.upc.dsa.models.User;
import edu.upc.dsa.transferObject.ObjectInformation;
import edu.upc.dsa.transferObject.UserInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Api(value = "/shop", description = "Endpoint to Shop Service")
@Path("/shop")

public class ShopService {
    private ShopManager sm;

    public ShopService() throws UserAlreadyExistsException {
        this.sm = ShopManagerImpl.getInstance();
        if (sm.size()==0) {
            Credentials credentials1 = new Credentials("guillempurti@gmail.com", "1234");
            sm.addUser("Guillem", "Purti", "22/01/2012", credentials1);
            Credentials credentials2 = new Credentials("albaroma@gmail.com", "1456");
            sm.addUser("Alba", "Roma", "15/03/2013", credentials2);
            Credentials credentials3 = new Credentials("marcmoran@gmail.com", "1234");
            sm.addUser("Marc", "Moran", "04/10/2018", credentials3);

            sm.addObject("Ordinador", "ordinador negre", 20);
            sm.addObject("Cadira", "de plastic blau", 10);
            sm.addObject("Ratoli", "color blanc", 5);
        }
    }

    @GET
    @ApiOperation(value = "get a UserList", notes = "UserList")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = UserInformation.class, responseContainer="List"),
    })
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlphabeticUserList() {
            List<User> userlist = this.sm.getAlphabeticUserList();
            GenericEntity<List<UserInformation>> entity = new GenericEntity<List<UserInformation>>(getAlphabeticUserInfoList(userlist)) {};
            return Response.status(201).entity(entity).build();
    }
    public List<UserInformation> getAlphabeticUserInfoList(List<User> userlist){
        List<UserInformation> userinfolist = new ArrayList<>();
        for(User u:userlist){
            UserInformation userInformation = new UserInformation(u.getName(),u.getSurname(), u.getDate(),u.getCredentials());
            userinfolist.add(userInformation);
        }
        return userinfolist;
    }



    @GET
    @ApiOperation(value = "get a ObjectList", notes = "ObjectList")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Obj.class, responseContainer="List"),
    })
    @Path("/object")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectList() {

        List<Obj> objList = this.sm.getObjectList();
        GenericEntity<List<Obj>> entity = new GenericEntity<List<Obj>>(objList) {};
        return Response.status(201).entity(entity).build() ;

    }


    @POST
    @ApiOperation(value = "create a new User", notes = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= UserInformation.class),
            @ApiResponse(code = 500, message = "Some parameter is null"),
            @ApiResponse(code = 409, message = "User already exists")

    })

    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(UserInformation user) {
        if (user.getName()==null || user.getSurname()==null || user.getDate()==null || user.getCredentials()==null)  return Response.status(500).entity(user).build();
        try{
            this.sm.addUser(user.getName(),user.getSurname(),user.getDate(),user.getCredentials());
            return Response.status(201).entity(user).build();
        }
        catch (UserAlreadyExistsException e){
            return Response.status(409).entity(user).build();
        }

    }

    @POST
    @ApiOperation(value = "create a new Object", notes = "Object addition")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= ObjectInformation.class),
            @ApiResponse(code = 500, message = "Some parameter is null or not valid")

    })

    @Path("/object")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addObject(ObjectInformation obj) {
        if (obj.getName()==null || obj.getDescription()==null || obj.getCoins()<0)  return Response.status(500).entity(obj).build();
        this.sm.addObject(obj.getName(), obj.getDescription(),obj.getCoins());
        return Response.status(201).entity(obj).build();
    }

    @PUT
    @ApiOperation(value = "buy an Object", notes = "buy Object")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 403, message = "Not enough money")
    })
    @Path("/user/{id}/{name}")
    public Response buyObject(@PathParam("name")String name,@PathParam("id") String id) {

        try{
            List<Obj> objList = this.sm.buyObject(name,id);
            return Response.status(201).build();
        }
        catch (InvalidCredentialsException e){
            return Response.status(401).build();
        }
        catch (NotEnoughMoneyException e){
            return Response.status(403).build();
        }

    }

    @POST
    @ApiOperation(value = "login a User", notes = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Credentials.class),
            @ApiResponse(code = 404, message = "User doesn't exists")
    })

    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userLogin(Credentials credentials) throws InvalidCredentialsException {
        //if (credentials.getEmail()==null || credentials.getPassword()==null)  return Response.status(500).entity(credentials).build();
        //this.sm.userLogin(credentials);
        //return Response.status(201).entity(credentials).build();

        try{
            this.sm.userLogin(credentials);
            return Response.status(201).entity(credentials).build();
        }
        catch (InvalidCredentialsException e){

            return Response.status(404).entity(credentials).build();
        }

    }
}
