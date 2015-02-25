package com.depaul.edu.se491.service.query;

import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.GenericType;

import java.util.List;

public interface QueryService {

    public String executeQuery(String resourceId, SoqlQuery query) throws LongRunningQueryException, SodaError;

    public List executeQuery(String resourceId, SoqlQuery query, GenericType type) throws SodaError, InterruptedException;

}
