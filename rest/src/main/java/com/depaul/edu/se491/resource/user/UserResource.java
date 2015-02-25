package com.depaul.edu.se491.resource.user;

import com.depaul.edu.se491.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by adampodraza on 2/21/15.
 */
@Component
@Path("/users")
public class UserResource {

    @Autowired
    UserService userService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers(
            @QueryParam("orderByInstertionDate") boolean orderByInsertionDate
    ) {

        return userService.getUsers(orderByInsertionDate);

    }

    @GET
    @Path("/get_user_by_id")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(
            @QueryParam("id") Long id
    ) {
        User userById = userService.getUserById(id);

        return Response.status(200)
                .entity(userById)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .build();
    }

    @GET
    @Path("/get_user_by_uuid")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByUuid(
            @QueryParam("uuid") String uuid
    ) {

        User userByUuid = userService.getUserByUuid(uuid);

        return Response.status(200)
                .entity(userByUuid)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .build();

    }

    @GET
    @Path("get_user_by_email")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByEmail(
            @QueryParam("email") String email
    ) {

        User userByEmail = userService.getUserByEmail(email);

        return Response.status(200)
                .entity(userByEmail)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .build();
    }


    @POST
    @Path("/disable_user_by_id")
    @Produces({MediaType.TEXT_HTML})
    public Response disableUserById(
            @QueryParam("id") Long id
    ) {

        userService.disableUserById(id);

        return Response.status(Response.Status.OK)
                .entity("User " + id + " has been disabled.")
                .build();

    }


    @POST
    @Path("/disable_all_users")
    @Produces({MediaType.TEXT_HTML})
    public Response disableAllUsers() {


        userService.disableAllUsers();

        return Response.status(Response.Status.OK)
                .entity("All users have been disabled.")
                .build();

    }

    @POST
    @Path("/disable_user_by_uuid")
    @Produces({MediaType.TEXT_HTML})
    public Response disableUserByUuid(
            @QueryParam("uuid") String uuid
    ) {
        userService.disableUserByUuid(uuid);

        return Response.status(Response.Status.OK)
                .entity("User " + uuid + " has been disabled.")
                .build();
    }

    @POST
    @Path("/enable_all_users")
    @Produces({MediaType.TEXT_HTML})
    public Response enableAllUsers() {

        userService.enableAllUsers();

        return Response.status(Response.Status.OK)
                .entity("All users are enabled.")
                .build();
    }


    @POST
    @Path("/enable_user_by_id")
    @Produces({MediaType.TEXT_HTML})
    public Response enableUserById(
            @QueryParam("id") Long id
    ){

        userService.enableUserById(id);

        return Response.status(Response.Status.OK)
                .entity("User " + id + " was created.")
                .build();

    }

    @POST
    @Path("/enable_user_by_uuid")
    @Produces({MediaType.TEXT_HTML})
    public Response enableUserByUuid(
            @QueryParam("uuid") String uuid
    ){
        userService.enableUserByUuid(uuid);

        return Response.status(Response.Status.OK)
                .entity("User " + uuid + " was created.")
                .build();
    }


    @POST
    @Path("/update_user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public Response updateUser(User u){

        userService.updateUser(u);

        return Response.status(Response.Status.OK)
            .entity("User " + u.getUuid() + " was updated.")
            .build();
    }

    @POST
    @Path("/create_user")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public Response createUser(User u) {
        Long id = userService.createUser(u);
        User c_u = userService.getUserById(id);

        return Response.status(Response.Status.OK)
                .entity("User " + c_u.getUuid() + " was created.")
                .build();
    }

}
