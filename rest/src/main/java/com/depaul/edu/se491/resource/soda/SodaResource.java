package com.depaul.edu.se491.resource.soda;

import com.depaul.edu.se491.service.query.QueryService;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.OrderByClause;
import com.socrata.model.soql.SoqlQuery;
import com.socrata.model.soql.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by adampodraza on 2/3/15.
 *
 * Resource controller takes requests and returns either a List of java objects or a Json string
 *
 */
@Component
@Path("/soda")
public class SodaResource {

    private static final Logger logger = LoggerFactory.getLogger(SodaResource.class);

    private SoqlQuery soqlQuery;

    @Autowired
    private QueryService queryService;


    //Get method to return all food inspections as json string
    @GET
    @Path("food_inspection")
    @Produces(value = {"text/plain"})
    public String getFoodInspectionQuery() throws InterruptedException, SodaError, LongRunningQueryException {
        long startTime = System.currentTimeMillis();
        String result = queryService.executeQuery("4ijn-s7e5", SoqlQuery.SELECT_ALL);
        long endTime   = System.currentTimeMillis();
        logger.debug("********************************************");
        logger.debug(String.format("food_inspection resource took %s miliseconds to complete", endTime - startTime));
        logger.debug("********************************************");

        return result;
    }

    //returns a list of food inspection objects, takes parameter "limit" to specify how many are returned
    @GET
    @Path("food_inspection_list")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<FoodInspection> getFoodInspectionListQuery(
            @DefaultValue("5") @QueryParam("limit") Integer limit) throws Exception {

        soqlQuery = new SoqlQueryBuilder()
                .setLimit(limit)
                .addOrderByPhrase(new OrderByClause(SortOrder.Descending, "inspection_date"))
                .build();

        long startTime = System.currentTimeMillis();
        List<FoodInspection> result = queryService.executeQuery("4ijn-s7e5", soqlQuery, FoodInspection.LIST_TYPE);
        long endTime   = System.currentTimeMillis();
        logger.debug("********************************************");
        logger.debug(String.format("food_inspection_list resource took %s miliseconds to complete", endTime - startTime));
        logger.debug("********************************************");

        return result;

    }

    //get method to return all crimes as json string
    @GET
    @Path("crime")
    @Produces(value = {"text/plain"})
    public String getCrimeQuery() throws LongRunningQueryException, SodaError {
        long startTime = System.currentTimeMillis();
        String result = queryService.executeQuery("ijzp-q8t2", SoqlQuery.SELECT_ALL);
        long endTime   = System.currentTimeMillis();
        logger.debug("********************************************");
        logger.debug(String.format("crime resource took %s miliseconds to complete", endTime - startTime));
        logger.debug("********************************************");

        return result;
    }

    //returns a list of crime objects, takes parameter "limit" to specify how many objects are returned
    @GET
    @Path("crime_list")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Crime> getCrimeListQuery(
            @DefaultValue("5") @QueryParam("limit") Integer limit) throws InterruptedException, SodaError {

        soqlQuery = new SoqlQueryBuilder()
                .setLimit(limit)
                .addOrderByPhrase(new OrderByClause(SortOrder.Descending, "date"))
                .build();

        long startTime = System.currentTimeMillis();
        List<Crime> result = queryService.executeQuery("ijzp-q8t2", soqlQuery, Crime.LIST_TYPE);
        long endTime   = System.currentTimeMillis();
        logger.debug("********************************************");
        logger.debug(String.format("crime_list resource took %s miliseconds to complete", endTime - startTime));
        logger.debug("********************************************");

        return result;
    }

}
