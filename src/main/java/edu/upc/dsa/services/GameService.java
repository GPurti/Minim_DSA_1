package edu.upc.dsa.services;


import edu.upc.dsa.*;
import edu.upc.dsa.models.Act;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import edu.upc.dsa.transferObject.ActualLevelInfo;
import edu.upc.dsa.transferObject.PointsInfo;
import edu.upc.dsa.transferObject.UpgradeLevelInfo;
import edu.upc.dsa.transferObject.UserIdInformation;
import io.swagger.annotations.*;


import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Api(value = "/examGame", description = "Endpoint to Game Service")
@Path("/examGame")

public class GameService {
    private GameManager sm;


    public GameService() throws UserAlreadyGameException, InvalidGameException, InvalidUserException {
        this.sm = GameManagerImpl.getInstance();
        if (sm.numUsers()==0) {
            sm.addUser("qwer");
            sm.addUser("zxcv");
            sm.addUser("poiu");
            sm.createGame("1111", "partida de rol",3);
            this.sm.startGame("1111","zxcv" );
            sm.createGame("2222", "partida de tiros",3);
        }
    }

    @POST
    @ApiOperation(value = "Create a new Game", notes = "Register game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Game.class),
    })
    @Path("/game")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(Game game) {

        this.sm.createGame(game.getId(),game.getDescription(),game.getNumlevels());
        return Response.status(201).entity(game).build();
    }

    @PUT
    @ApiOperation(value = "start a Game", notes = "start Game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 401, message = "Invalid user"),
            @ApiResponse(code = 403, message = "Invalid game"),
            @ApiResponse(code = 409, message = "The user is already in game")
    })
    @Path("/{idUser}/{idGame}")
    public Response startGame(@PathParam("idUser")String idUser,@PathParam("idGame") String idGame) {
        try{
            this.sm.startGame(idGame,idUser);
            return Response.status(201).build();
        }
        catch (InvalidUserException e){
            return Response.status(401).build();
        }
        catch (InvalidGameException e){
            return Response.status(403).build();
        }
        catch (UserAlreadyGameException e){
            return Response.status(409).build();
        }
    }

    @GET
    @ApiOperation(value = "Get user's level", notes = "UserLevel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = ActualLevelInfo.class, responseContainer="List"),
            @ApiResponse(code = 401, message = "Invalid user"),
            @ApiResponse(code = 409, message = "The user is not in game")
    })
    @Path("/users/{idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLevel(@PathParam("idUser") String id) {
        try{
            ActualLevelInfo levelInfo = this.sm.getUserLevel(id);
            GenericEntity<ActualLevelInfo> entity = new GenericEntity<ActualLevelInfo>(levelInfo) {};
            return Response.status(201).entity(entity).build();
        }
        catch (InvalidUserException e){
            return Response.status(401).build();
        }
        catch (UserNotInGameException e){
            return Response.status(409).build();
        }
    }
    @GET
    @ApiOperation(value = "Get a user's actual points", notes = "UserPoints")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PointsInfo.class, responseContainer="List"),
            @ApiResponse(code = 401, message = "Invalid user"),
            @ApiResponse(code = 409, message = "The user is not in game")
    })
    @Path("/users/{idUser}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoints(@PathParam("idUser") String id) {
        try{
            Integer pointsInfo = this.sm.getPoints(id);
            PointsInfo pInfo = new PointsInfo(pointsInfo);
            GenericEntity<PointsInfo> entity = new GenericEntity<PointsInfo>(pInfo) {};
            return Response.status(201).entity(entity).build();
        }
        catch (InvalidUserException e){
            return Response.status(401).build();
        }
        catch (UserNotInGameException e){
            return Response.status(409).build();
        }
    }

    @PUT
    @ApiOperation(value = "Upgrade a level", notes = "Level upgrade")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 401, message = "Invalid user"),
            @ApiResponse(code = 409, message = "The user is not in game")
    })
    @Path("/users/levelUpgrade")
    public Response upgradeLevel(UpgradeLevelInfo levelInfo) {
        try{
            this.sm.upgradeLevel(levelInfo.getIdUser(), levelInfo.getPoints(), levelInfo.getDate());
            return Response.status(201).build();
        }
        catch (InvalidUserException e){
            return Response.status(401).build();
        }
        catch (UserNotInGameException e){
            return Response.status(409).build();
        }
    }

    @PUT
    @ApiOperation(value = "End the user's game", notes = "endGame")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 401, message = "Invalid user"),
            @ApiResponse(code = 409, message = "The user is not in game")
    })
    @Path("/users/{idUser}/endGame")
    public Response endGame(@PathParam("idUser") String idUser) {
        try{
            this.sm.endGame(idUser);
            return Response.status(201).build();
        }
        catch (InvalidUserException e){
            return Response.status(401).build();
        }
        catch (UserNotInGameException e){
            return Response.status(409).build();
        }
    }

    @GET
    @ApiOperation(value = "Get a user list ordered by points of a game", notes = "UserList by points")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = UserIdInformation.class, responseContainer="List"),
            @ApiResponse(code = 403, message = "Invalid game")
    })
    @Path("/user/{idGame}/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByPoints(@PathParam("idGame") String id) {
        try {
            List<User> userList = this.sm.getUsersByPoints(id);
            List<UserIdInformation> userIdList= getAlphabeticUserInfoList(userList);
            GenericEntity<List<UserIdInformation>> entity = new GenericEntity<List<UserIdInformation>>(userIdList) {};
            return Response.status(201).entity(entity).build();
        }
        catch (InvalidGameException e){
            return Response.status(403).build();
        }
    }

    public List<UserIdInformation> getAlphabeticUserInfoList(List<User> userList){
        List<UserIdInformation> userIdInformationList = new ArrayList<>();
        for(User u:userList){
            UserIdInformation infoIdUser = new UserIdInformation(u.getIdUser(),u.getPoints(), u.getIdGame());
            userIdInformationList.add(infoIdUser);
        }
        return userIdInformationList;
    }

    @GET
    @ApiOperation(value = "Get the games a user has played", notes = "Game list")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class, responseContainer="List"),
            @ApiResponse(code = 401, message = "Invalid user")
    })
    @Path("/games/{idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames(@PathParam("idUser") String id) {
        try {
            List<String> gameList = this.sm.getGames(id);
            GenericEntity<List<String>> entity = new GenericEntity<List<String>>(gameList) {};
            return Response.status(201).entity(entity).build();
        }
        catch (InvalidUserException e){
            return Response.status(401).build();
        }
    }

    @GET
    @ApiOperation(value = "Get the activities from a user's game", notes = "Activities list")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Act.class, responseContainer="List"),
            @ApiResponse(code = 401, message = "Invalid user")
    })
    @Path("/users/{idUser}/{idGame}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities(@PathParam("idGame") String idGame, @PathParam("idUser") String idUser) {
        List<Act> actList= sm.getActivities(idGame,idUser);
        GenericEntity<List<Act>> entity = new GenericEntity<List<Act>>(actList) {};
        return Response.status(201).entity(entity).build();
    }

}
