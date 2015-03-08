package com.depaul.edu.se491.service.mongo;

import com.depaul.edu.se491.dao.mongo.MongoDao;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;

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
}
