package com.depaul.edu.se491.dao.alert;

import java.util.List;

/**
 * Created by Tom Mitic on 2/15/15.
 */
public interface AlertQueriesDao {

    public List<AlertQueriesEntity> getAllAlerts();

    /**
     * Get all alert queries for a particular user.
     *
     * @param id
     * @return List<AlertQueriesEntity>
     */
    public List<AlertQueriesEntity> getAlertQueriesByUserId(Long id);

    /**
     * Get all alert queries for a particular user.
     *
     * @param uuid
     * @return List<AlertQueriesEntity>
     */
    public List<AlertQueriesEntity> getAlertQueriesByUserUuid(String uuid);

    /**
     * Get an alert query by it's id.
     *
     * @param id
     * @return AlertQueriesEntity
     */
    public AlertQueriesEntity getAlertQueryById(Long id);

    /**
     * Disable the alert query.
     *
     * @param id
     */
    public void disableAlertQuery(Long id);

    /**
     * Enable the alert query.
     *
     * @param id
     */
    public void enableAlertQuery(Long id);

    /**
     * Update alert query.
     *
     * @param aqe
     */
    public void updateAlertQuery(AlertQueriesEntity aqe);

    /**
     * Create an alert query
     *
     * @param aqe
     * @return id
     */
    public Long createAlertQuery(AlertQueriesEntity aqe);
}
