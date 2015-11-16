package com.depaul.edu.se491.resource.chart;

import com.depaul.edu.se491.service.chart.ChartStatsService;
import com.depaul.edu.se491.service.mongo.MongoService;
import com.mongodb.DBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

/**
 * Created by Tu Vo on 5/18/15.
 */
@Component
@Path("/chart")
public class ChartStatsResource {

    @Autowired
    private MongoService ser;
    
    @Autowired
    private ChartStatsService chart;
    
    
    @GET
    @Path("getCollectionByNames/{collection}")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<DBObject> findAll(@PathParam("collection") String collection) {
    	
    	List<DBObject> result =null;
    	String coll = collection;
    	
    	switch (coll) {
    	case "crimes": 
        	result = chart.CaculateCrimesStats(collection);
        	break;
    	case "potholes":
    		result = chart.CaculatePotholesStats(collection);
    		break;
    	case "salaries":
    		result = chart.CaculateSalariesStats(collection);
    		break;
    	case "foodinspections":
    		result = chart.CaculateViolationsStats(collection);
    		break;
    	}
    	
        return result;
    }

  
}
