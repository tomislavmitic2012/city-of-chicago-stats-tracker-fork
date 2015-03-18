package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;
import com.depaul.edu.se491.service.email.EmailService;
import com.depaul.edu.se491.service.mongo.MongoService;
import com.mongodb.DBCursor;

public class AlertRunnable implements Runnable {

    private AlertQueriesEntity alert;
    private MongoService mongoService;
    private EmailService emailService;
    private String email;

    public AlertRunnable(AlertQueriesEntity alert, MongoService mongoService, EmailService emailService) {
        this.alert = alert;
        this.mongoService = mongoService;
        this.emailService = emailService;
        this.email = alert.getUser().getEmail();
    }

    @Override
    public void run() {
        DBCursor cursor = mongoService.find(alert.getQuery());
        if (cursor.count() > 0) {
            emailService.sendEmail(email, "message");
        }
    }
}
