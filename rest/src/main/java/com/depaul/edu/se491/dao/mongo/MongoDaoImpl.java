package com.depaul.edu.se491.dao.mongo;

import com.depaul.edu.se491.config.AppConfig;
import com.mongodb.*;
import com.mongodb.jee.MongoHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Set;

import com.mongodb.util.JSON;

/**
 * Created by Tom Mitic on 2/28/15.
 */
public class MongoDaoImpl implements MongoDao {

    private static final Logger logger = LoggerFactory.getLogger(MongoDaoImpl.class);

    private static Properties prop;

    static {
        prop = new Properties();
        InputStream in = MongoDaoImpl.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("Enable to get app.properties", e);
        }
        try {
            AppConfig.setUpMongo();
        } catch (UnknownHostException e) {
            logger.error("Unable to connect to mongo", e);
        }
    }

    private DB db = MongoHolder.connect().getDB(prop.getProperty("app.mongo_socrata_db"));

    @Override
    public DBObject findOne(String collection) {
        return db.getCollection(collection).findOne(
                new BasicDBObject(), new BasicDBObject("_id", false));
    }

    @Override
    public DBCursor find(String collection) {
        return db.getCollection(collection).find(
                new BasicDBObject(), new BasicDBObject("_id", false)
        ).sort(new BasicDBObject(
                "_id", 1)).limit(Integer.parseInt(
                prop.getProperty("app.mongo_query_limit")));
    }
    
    @Override
    public DBCursor findCollection(String collection) {     
    	 return db.getCollection(collection).find().sort(new BasicDBObject(
                 "_id", -1)).limit(Integer.parseInt(
                         prop.getProperty("app.mongo_query_limit100k")));
    }


    @Override
    public DBObject findOne(String collection, String params) {
        return db.getCollection(collection).findOne(
                (BasicDBObject) JSON.parse(params),
                new BasicDBObject("_id", false)
        );
    }

    @Override
    public DBCursor find(String collection, String params) {
        return db.getCollection(collection).find(
                (BasicDBObject) JSON.parse(params),
                new BasicDBObject("_id", false)
        ).sort(new BasicDBObject(
                "_id", 1)).limit(Integer.parseInt(
                prop.getProperty("app.mongo_query_limit")));
    }

    @Override
    public Set<String> getCollectionNames() {
        return db.getCollectionNames();
    }

    @Override
    public void setDB(DB db) {
        this.db = db;
    }
}
