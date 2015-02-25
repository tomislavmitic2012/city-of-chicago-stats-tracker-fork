package com.depaul.edu.se491.service.query;

import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import java.util.List;

public class QueryServiceImpl implements QueryService {

    Soda2Consumer consumer;

    public QueryServiceImpl(String url, String user, String password, String token) {
        consumer = Soda2Consumer.newConsumer(url, user, password, token);
    }

    public String executeQuery(String resourceId, SoqlQuery query) throws LongRunningQueryException, SodaError {
        ClientResponse response = consumer.query(resourceId, HttpLowLevel.JSON_TYPE,query);
        return response.getEntity(String.class);
    }

    @Override
    public List executeQuery(String resourceId, SoqlQuery query, GenericType type) throws SodaError, InterruptedException {
        return consumer.query(resourceId, query, type);
    }
}
