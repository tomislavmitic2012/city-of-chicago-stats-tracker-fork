package com.depaul.edu.se491.dao.mongo;

import com.mongodb.*;
import com.mongodb.jee.MongoHolder;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * Created by Tom Mitic on 2/28/15.
 */
public interface MongoDao {

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
     * Returns the all records from mongo collection.
     *
     * @return DBCursor
     */
    public DBCursor findCollection(String collection);

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
     * Return all of the collections located within this respective DB.
     *
     * @return Set<String>
     */
    public Set<String> getCollectionNames();

    /**
     * Used for unit testing
     *
     * @param db
     */
    public void setDB(DB db);

    /**
     * A helper test for unit testing
     */
    public static void setDefaultMongo(String uri) throws UnknownHostException {
        MongoHolder.connect(new MongoClientURI(uri), true);
    }
}
