package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.resource.alert.AlertQueries;

import java.util.List;

/**
 * Created by Tom Mitic on 2/17/15.
 */
public class AlertQueriesServiceImpl implements AlertQueriesService {


    @Override
    public List<AlertQueries> getAlertQueriesByUserId(Long id) {
        return null;
    }

    @Override
    public List<AlertQueries> getAlertQueriesByUserUuid(String uuid) {
        return null;
    }

    @Override
    public AlertQueries getAlertQueryById(Long id) {
        return null;
    }

    @Override
    public void disableAlertQuery(Long id) {

    }

    @Override
    public void enableAlertQuery(Long id) {

    }

    @Override
    public void updateAlertQuery(AlertQueries aqe) {

    }

    @Override
    public Long createAlertQuery(AlertQueries aqe) {
        return null;
    }
}
