package com.depaul.edu.se491.service.alert;

import com.depaul.edu.se491.resource.alert.AlertQueries;

import java.util.List;

/**
 * Created by Tom Mitic on 2/16/15.
 */
public interface AlertQueriesService {

    /**
     * Get all alert queries for a particular user.
     *
     * @param id
     * @return List<AlertQueriesEntity>
     */
    public List<AlertQueries> getAlertQueriesByUserId(Long id);

    /**
     * Get all alert queries for a particular user.
     *
     * @param uuid
     * @return List<AlertQueriesEntity>
     */
    public List<AlertQueries> getAlertQueriesByUserUuid(String uuid);

    /**
     * Get an alert query by it's id.
     *
     * @param id
     * @return AlertQueriesEntity
     */
    public AlertQueries getAlertQueryById(Long id);

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
    public void updateAlertQuery(AlertQueries aqe);

    /**
     * Create an alert query
     *
     * @param aqe
     * @return id
     */
    public Long createAlertQuery(AlertQueries aqe);
}