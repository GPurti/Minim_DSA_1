package edu.upc.dsa.services;


import edu.upc.dsa.*;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/game")

public class GameService {
    private GameManager sm;


    public GameService() throws UserAlreadyGameException, InvalidGameException, InvalidUserException {
        this.sm = GameManagerImpl.getInstance();
        if (sm.size()==0) {
            sm.addUser("qwer");
            sm.addUser("zxcv");
            sm.addUser("poiu");
            sm.createGame("1111", "partida de rol",3);
            this.sm.startGame("1111","zxcv" );
            sm.createGame("2222", "partida de tiros",3);
        }
    }

    @GET
    @ApiOperation(value = "get a UserList", notes = "UserList")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByPoints() {
            List<User> userlist = this.sm.getUsersByPoints();
            return Response.status(201).entity(entity).build();
    }
    /*
    public List<UserIdInformation> getAlphabeticUserInfoList(List<User> userlist){
        List<UserIdInformation> useridinfolist = new ArrayList<>();
        for(User u:userlist){
            UserIdInformation infoidUser = new UserIdInformation(u.getName(),u.getSurname(), u.getDate(), u.getId());
            useridinfolist.add(infoidUser);
        }
        return useridinfolist;
    }*/

    @GET
    @ApiOperation(value = "get games", notes = "Gamelist")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class, responseContainer="List"),
    })
    @Path("/object/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameList(@PathParam("id") String id) {

        List<Game> gamelist= this.sm.getGames(id);
        GenericEntity<List<Game>> entity = new GenericEntity<List<Game>>(game) {};
        return Response.status(201).entity(entity).build() ;

    }


    @POST
    @ApiOperation(value = "create a new Game", notes = "Register game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Game.class),

    })

    @Path("/game")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(Game game) {

        this.sm.createGame(game.getId(),game.getDescription(),game.getNumlevels());
        return Response.status(201).entity(game).build();

    }

}
