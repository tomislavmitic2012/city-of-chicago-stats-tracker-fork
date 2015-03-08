package com.depaul.edu.se491.resource.mongo;

import com.depaul.edu.se491.service.mongo.MongoService;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Tom Mitic on 3/1/15.
 */
@Component
@Path("/mongo")
public class MongoResource {

    @Autowired
    private MongoService ser;

    /**
     * Returns the first available crime result from mongo.
     *
     * @param collection
     * @return DBObject marshalled into JSON
     */
    @GET
    @Path("findOne/{collection}")
    @Produces({MediaType.APPLICATION_JSON })
    public DBObject findOne(@PathParam("collection") String collection) {
        return ser.findOne(collection);
    }

    /**
     * Returns the first available result from Mongo DB based on the
     * search parameters passed in.
     *
     * @param collection
     * @param params
     * @return DBObject marchalled to JSON
     */
    @GET
    @Path("findOneByParams/{collection}")
    @Produces({MediaType.APPLICATION_JSON })
    public DBObject findOne(@PathParam("collection") String collection,
                                      @QueryParam("params") String params) {
        return ser.findOne(collection, params);
    }

    /**
     * Returns the Top 50 records from mongo newest to oldest based
     * on the passed in search parameters.
     *
     * @param collection
     * @return DBCursor marchalled to JSON
     */
    @GET
    @Path("findTop50/{collection}")
    @Produces({MediaType.APPLICATION_JSON })
    public List<DBObject> find(@PathParam("collection") String collection) {
        return ser.find(collection).toArray();
    }

    /**
     * Returns the first available result from Mongo DB based on the
     * search parameters passed in.
     *
     * @param collection
     * @return DBCursor marchalled to JSON
     */
    @GET
    @Path("findAllByParams/{collection}")
    @Produces({MediaType.APPLICATION_JSON })
    public List<DBObject> find(@PathParam("collection") String collection,
                         @QueryParam("params") String params) {
        return ser.find(collection, params).toArray();
    }
}
