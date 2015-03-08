package com.depaul.edu.se491.resource.alert;

import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.service.alert.AlertQueriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/alerts")
public class AlertResource {

    @Autowired
    AlertQueriesService userAlertQueriesService;

    @GET
    @Path("/get_alert_by_id")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(
            @QueryParam("id") Long id
    ) {
        AlertQueriesEntity alert = userAlertQueriesService.getAlertQueryById(id);

        return Response.status(200)
                .entity(alert)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .build();
    }

    @GET
    @Path("/get_alert_by_uuid")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByUuid(
            @QueryParam("id") Long id
    ) {
        List<AlertQueriesEntity> alert = userAlertQueriesService.getAlertQueriesByUserId(id);

        return Response.status(200)
                .entity(alert)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .build();
    }


    @POST
    @Path("/disable_alert_by_id")
    @Produces({MediaType.TEXT_HTML})
    public Response disableUserById(
            @QueryParam("id") Long id
    ) {

        userAlertQueriesService.disableAlertQuery(id);

        return Response.status(Response.Status.OK)
                .entity("Alert " + id + " has been disabled.")
                .build();

    }

    @POST
    @Path("/enable_alert_by_id")
    @Produces({MediaType.TEXT_HTML})
    public Response enableUserById(
            @QueryParam("id") Long id
    ){

        userAlertQueriesService.enableAlertQuery(id);

        return Response.status(Response.Status.OK)
                .entity("Alert " + id + " was created.")
                .build();

    }


    @POST
    @Path("/update_alert")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public Response updateUser(AlertQueriesEntity alertQueriesEntity) throws AppException {

        userAlertQueriesService.updateAlertQuery(alertQueriesEntity);

        return Response.status(Response.Status.OK)
            .entity("Alert " + alertQueriesEntity.getId() + " was updated.")
            .build();
    }

    @POST
    @Path("/create_alert")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    public Response createUser(AlertQueriesEntity alert) throws AppException {
        Long id = userAlertQueriesService.createAlertQuery(alert);

        return Response.status(Response.Status.OK)
                .entity("Alert " + id + " was created.")
                .build();
    }

}
