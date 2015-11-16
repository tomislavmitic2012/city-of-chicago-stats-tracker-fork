package com.depaul.edu.se491.dao.mongo;

import com.mongodb.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Tom Mitic on 2/28/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MongoDaoImplTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static BasicDBObject result = new BasicDBObject();

    static {
        result.append("ID", "9969168");
        result.append("ID", "9969168");
        result.append("Case Number", "HY158467");
        result.append("Date", "02/20/2015 11:56:00 PM");
        result.append("Block", "054XX W HADDON AVE");
    }

    private static final String CRIME_QUERY = "{'Date' : '02/20/2015 11:56:00 PM'}";
    private static final String BLOCK = "054XX W HADDON AVE";
    private static final DBObject O = new BasicDBObject(result);
    private static final List<DBObject> l = new ArrayList<>();
    static {
        l.add(O);
    }
    private static final String CRIMES = "crimes";

    private static final Set<String> names;
    static {
        names = new HashSet<>(Arrays.asList(new String[] {
                "foodinspections", "crimes", "salaries", "potholes"
        }));
    }

    MongoDao dao;

    @Mock
    DB db;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    DBCollection collection;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    DBCursor cursor;

    @Before
    public void setUp() {
        dao = new MongoDaoImpl();
        dao.setDB(db);
    }

    @Test
    public void findOne() {
        when(db.getCollection(CRIMES)).thenReturn(collection);
        when(dao.findOne(CRIMES)).thenReturn(O);

        DBObject o = dao.findOne(CRIMES);

        assertNotNull(o);
        assertTrue(O.equals(o));
    }

    @Test
    public void find() {
        when(db.getCollection(CRIMES)).thenReturn(collection);
        when(dao.find(CRIMES)).thenReturn(cursor);

        DBCursor c = dao.find(CRIMES);
        assertNotNull(c);
        assertNotEquals(l.size(), c.toArray().size());
    }

    @Test
    public void findOneWithParameter() {
        when(db.getCollection(CRIMES)).thenReturn(collection);
        when(dao.findOne(CRIMES, CRIME_QUERY)).thenReturn(O);

        DBObject o = dao.findOne(CRIMES, CRIME_QUERY);
        assertNotNull(o);
        assertEquals(BLOCK, o.get("Block"));
    }

    @Test
    public void findWithParameter() {
        when(db.getCollection(CRIMES)).thenReturn(collection);
        when(dao.find(CRIMES, CRIME_QUERY)).thenReturn(cursor);

        DBCursor c = dao.find(CRIMES, CRIME_QUERY);
        assertNotNull(c);
        assertNotEquals(l.size(), c.toArray().size());
    }

    @Test
    public void getCollectionNames() {
        when(db.getCollectionNames()).thenReturn(names);
        when(dao.getCollectionNames()).thenReturn(names);

        Set<String> n = dao.getCollectionNames();
        assertNotNull(n);
        assertEquals(4, n.size());
    }
}
