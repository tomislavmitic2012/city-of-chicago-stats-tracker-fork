package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.dao.alert.AlertQueriesDao;
import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;
import com.depaul.edu.se491.resource.alert.AlertQueries;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Tom Mitic on 2/17/15.
 */
public class AlertQueriesServiceImpl implements AlertQueriesService {

    @Autowired
    private AlertQueriesDao alertQueriesDao;

    @Override
    public List<AlertQueriesEntity> getAlertQueriesByUserId(Long id) {
        return alertQueriesDao.getAlertQueriesByUserId(id);
    }

    @Override
    public List<AlertQueriesEntity> getAlertQueriesByUserUuid(String uuid) {
        return alertQueriesDao.getAlertQueriesByUserUuid(uuid);
    }

    @Override
    public AlertQueriesEntity getAlertQueryById(Long id) {
        return alertQueriesDao.getAlertQueryById(id);
    }

    @Override
    public void disableAlertQuery(Long id) {
        alertQueriesDao.disableAlertQuery(id);
    }

    @Override
    public void enableAlertQuery(Long id) {
        alertQueriesDao.enableAlertQuery(id);
    }

    @Override
    public void updateAlertQuery(AlertQueriesEntity aqe) {
        alertQueriesDao.updateAlertQuery(aqe);
    }

    @Override
    public Long createAlertQuery(AlertQueriesEntity aqe) {
        return alertQueriesDao.createAlertQuery(aqe);
    }
}
