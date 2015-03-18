package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;

import java.util.List;

public interface AlertRunnerService {

    List<AlertQueriesEntity> loadAlerts();
    boolean sendAlert(AlertQueriesEntity alert);

}
