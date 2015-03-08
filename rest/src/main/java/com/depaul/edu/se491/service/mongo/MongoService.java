package com.depaul.edu.se491.service.mongo;

import com.depaul.edu.se491.dao.mongo.MongoDao;
import com.mongodb.*;
import com.mongodb.jee.MongoHolder;

import java.net.UnknownHostException;

/**
 * Created by Tom Mitic on 2/28/15.
 */
public interface MongoService {

    /**
     * Returns the first available result from Mongo DB.
     *
     * @return DBObject
     */
    public DBObject findOne(String collection);

    /**
     * Returns the Top 50 records from mongo newest to oldest.
     *
     * @return DBCursor
     */
    public DBCursor find(String collection);

    /**
     * Returns the first available result from Mongo DB based on the
     * search parameters passed in.
     *
     * @return DBObject
     */
    public DBObject findOne(String collection, String params);

    /**
     * Returns the Top 50 records from mongo newest to oldest based
     * on the passed in search parameters.
     *
     * @return DBCursor
     */
    public DBCursor find(String collection, String params);

    /**
     * Used for unit testing.
     *
     * @param md
     */
    public void setMongoDao(MongoDao md);
}
