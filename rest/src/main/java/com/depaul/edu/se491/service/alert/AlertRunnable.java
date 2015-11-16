package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;
import com.depaul.edu.se491.service.email.EmailService;
import com.depaul.edu.se491.service.mongo.MongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;

import java.util.Date;

public class AlertRunnable implements Runnable {

    private AlertQueriesEntity alert;
    private MongoService mongoService;
    private EmailService emailService;
    private String email;
    private Date lastQuery;

    public AlertRunnable(AlertQueriesEntity alert, MongoService mongoService, EmailService emailService) {
        this.lastQuery = new Date();
        this.alert = alert;
        this.mongoService = mongoService;
        this.emailService = emailService;
        this.email = alert.getUser().getEmail();
    }

    @Override
    public void run() {
        BasicDBObject fields = (BasicDBObject) JSON.parse(alert.getQuery());
        fields.put("updated_ts", new BasicDBObject("$gt", lastQuery));
        DBCursor cursor = mongoService.find(alert.getCollection(), JSON.serialize(fields));
        if (cursor.count() > 0) {
            lastQuery = new Date();
            emailService.sendEmail(email, "Alert triggered");
        }
    }
}
