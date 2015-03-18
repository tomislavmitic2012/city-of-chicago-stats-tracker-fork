package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;
import com.depaul.edu.se491.service.email.EmailService;
import com.depaul.edu.se491.service.mongo.MongoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AlertRunnerServiceImpl implements AlertRunnerService {

    @Autowired
    private AlertQueriesService userAlertQueriesService;
    @Autowired
    private MongoService mongoService;
    @Autowired
    private EmailService emailService;

    private List<AlertQueriesEntity> alerts = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(AlertRunnerServiceImpl.class);
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    @PostConstruct
    public void init() {
        alerts.addAll(loadAlerts());
        for(AlertQueriesEntity alert : alerts) {
            if(alert.getInterval() != 0) {
                executor.scheduleAtFixedRate(new AlertRunnable(alert, mongoService, emailService), 0, alert.getInterval(), TimeUnit.MILLISECONDS);
            } else {
                logger.warn("Alert with id=" + alert.getId() + " couldn't be started because interval was 0.");
            }
        }
    }

    @Override
    public List<AlertQueriesEntity> loadAlerts() {
        return userAlertQueriesService.getAllAlerts();
    }

    @Override
    public boolean sendAlert(AlertQueriesEntity alert) {
        return false;
    }

    public AlertQueriesService getUserAlertQueriesService() {
        return userAlertQueriesService;
    }

    public void setUserAlertQueriesService(AlertQueriesService userAlertQueriesService) {
        this.userAlertQueriesService = userAlertQueriesService;
    }

    public MongoService getMongoService() {
        return mongoService;
    }

    public void setMongoService(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
