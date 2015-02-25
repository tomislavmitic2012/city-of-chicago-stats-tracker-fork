package com.depaul.edu.se491.service;

import com.depaul.edu.se491.service.query.QueryService;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import com.depaul.edu.se491.resource.soda.Station;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class QueryServiceTest {

    @Autowired
    QueryService queryService;

    @Test
    public void testQueryService() throws LongRunningQueryException, SodaError, InterruptedException {
        String results = queryService.executeQuery("z8bn-74gv", SoqlQuery.SELECT_ALL);
        junit.framework.Assert.assertNotNull(results);
        System.out.println(results);
        List<Station> stations = queryService.executeQuery("z8bn-74gv", SoqlQuery.SELECT_ALL, Station.LIST_TYPE);
        junit.framework.Assert.assertNotNull(stations);
        junit.framework.Assert.assertTrue("0 values returned", stations.size() > 0);
    }


}
