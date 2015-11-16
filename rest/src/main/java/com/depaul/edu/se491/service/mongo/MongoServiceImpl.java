package com.depaul.edu.se491.service.mongo;

import com.depaul.edu.se491.dao.mongo.MongoDao;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by Tom Mitic on 2/28/15.
 */
public class MongoServiceImpl implements MongoService {

    @Autowired
    MongoDao mongoDao;

    @Override
    public DBObject findOne(String collection) {
        return mongoDao.findOne(collection);
    }

    @Override
    public DBCursor find(String collection) {
        return mongoDao.find(collection);
    }
    
    @Override
    public DBCursor findCollection(String collection) {
        return mongoDao.findCollection(collection);
    }

    @Override
    public DBObject findOne(String collection, String params) {
        return mongoDao.findOne(collection, params);
    }

    @Override
    public DBCursor find(String collection, String params) {
        return mongoDao.find(collection, params);
    }

    @Override
    public void setMongoDao(MongoDao md) {
        this.mongoDao = md;
    }

    @Override
    public Set<String> getCollectionNames() {
        return mongoDao.getCollectionNames();
    }
}
