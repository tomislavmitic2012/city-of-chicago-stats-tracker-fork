package com.depaul.edu.se491.service;

import com.depaul.edu.se491.dao.mongo.MongoDao;
import com.depaul.edu.se491.service.mongo.MongoService;
import com.depaul.edu.se491.service.mongo.MongoServiceImpl;
import com.mongodb.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Tom Mitic on 2/28/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MongoServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static Map<String, String> result;
    static {
        result  = new HashMap<>();
        result.put("ID", "9969168");
        result.put("Case Number", "HY158467");
        result.put("Date", "02/20/2015 11:56:00 PM");
        result.put("Block", "054XX W HADDON AVE");
    }

    private static final String CRIME_QUERY = "{'Date' : '02/20/2015 11:56:00 PM'}";
    private static final DBObject O = new BasicDBObject(result);
    private static final String CRIMES = "crimes";

    MongoService ms;

    @Mock
    MongoDao dao;

    @Before
    public void setUp() {
        ms = new MongoServiceImpl();
        ms.setMongoDao(dao);
    }

    @Test
    public void findOneTest() {
        when(ms.findOne(CRIMES)).thenReturn(O);

        DBObject o = ms.findOne(CRIMES);
        verify(dao).findOne(CRIMES);
        verify(dao, never()).find(CRIMES);
        verify(dao, never()).findOne(CRIMES, CRIME_QUERY);
        verify(dao, never()).find(CRIMES, CRIME_QUERY);
        verify(dao, never()).getCollectionNames();

        assertTrue(O.equals(o));
    }

    @Test
    public void findOneTestWithSearchQuery() {
        when(ms.findOne(CRIMES, CRIME_QUERY)).thenReturn(O);

        DBObject o = ms.findOne(CRIMES, CRIME_QUERY);
        verify(dao).findOne(CRIMES, CRIME_QUERY);
        verify(dao, never()).find(CRIMES);
        verify(dao, never()).findOne(CRIMES);
        verify(dao, never()).find(CRIMES, CRIME_QUERY);
        verify(dao, never()).getCollectionNames();

        assertTrue(O.equals(o));
    }

    @Test
    public void findTest() {
        ms.find(CRIMES);
        verify(dao).find(CRIMES);
        verify(dao, never()).findOne(CRIMES, CRIME_QUERY);
        verify(dao, never()).findOne(CRIMES);
        verify(dao, never()).find(CRIMES, CRIME_QUERY);
        verify(dao, never()).getCollectionNames();
        verify(dao, never()).getCollectionNames();
    }

    @Test
    public void findTestWithSearchQuery() {
        ms.find(CRIMES, CRIME_QUERY);
        verify(dao).find(CRIMES, CRIME_QUERY);
        verify(dao, never()).find(CRIMES);
        verify(dao, never()).findOne(CRIMES, CRIME_QUERY);
        verify(dao, never()).findOne(CRIMES);
        verify(dao, never()).getCollectionNames();
    }

    @Test
    public void getCollectionNamesTest() {
        ms.getCollectionNames();
        verify(dao).getCollectionNames();
        verify(dao, never()).find(CRIMES, CRIME_QUERY);
        verify(dao, never()).find(CRIMES);
        verify(dao, never()).findOne(CRIMES, CRIME_QUERY);
        verify(dao, never()).findOne(CRIMES);
    }
}
