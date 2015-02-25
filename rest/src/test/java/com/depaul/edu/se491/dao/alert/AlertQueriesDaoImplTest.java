package com.depaul.edu.se491.dao.alert;

import com.depaul.edu.se491.dao.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tom Mitic on 2/15/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class AlertQueriesDaoImplTest {

    @Autowired
    protected AlertQueriesDao dao;

    @Autowired
    protected UserDao u_dao;

    @Test
    public void getAlertQueriesByUserIdTest() {
        List<AlertQueriesEntity> a_qs = dao.getAlertQueriesByUserId(1L);
        assertNotNull(a_qs);
        assertEquals("The length of the list is 2", 2, a_qs.size());
        assertEquals("The list is in desc id order", true, a_qs.get(0).getId() > a_qs.get(1).getId());
    }

    @Test
    public void getAlertQueriesByUserUuidTest() {
        List<AlertQueriesEntity> a_qs = dao.getAlertQueriesByUserUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        assertNotNull(a_qs);
        assertEquals("The length of the list is 2", 2, a_qs.size());
        assertEquals("The list is in desc order", true, a_qs.get(0).getId() > a_qs.get(1).getId());
    }

    @Test
    public void getAlertQueryByIdTest() {
        AlertQueriesEntity a = dao.getAlertQueryById(1L);
        assertNotNull(a);
        assertEquals("The correct alert query is brought back", "{j: {$ne: 3}, k: {$gt: 10} }", a.getQuery());

        a = dao.getAlertQueryById(100L);
        assertNotNull(a);
        assertNull("The id of the alert query is null", a.getId());
    }

    @Test
    public void disableAlertQueryTest() {
        dao.disableAlertQuery(3L);
        AlertQueriesEntity a = dao.getAlertQueryById(3L);
        assertNotEquals("The alert query is disabled", true, a.getEnabled());
    }

    @Test
    public void enableAlertQueryTest() {
        dao.enableAlertQuery(1L);
        AlertQueriesEntity a = dao.getAlertQueryById(1L);
        assertNotEquals("The alert query is enabled", false, a.getEnabled());
    }

    @Test
    public void updateAlertQueryTest() {
        AlertQueriesEntity a = dao.getAlertQueryById(1L);
        a.setQuery("{j: {$ne: 31}, k: {$gt: 10} }");
        dao.updateAlertQuery(a);
        a = dao.getAlertQueryById(1L);
        assertNotEquals("The value of the query has changed", "{j: {$ne: 3}, k: {$gt: 10} }", a.getQuery());
    }

    @Test
    public void createAlertQueryTest() {
        AlertQueriesEntity a = new AlertQueriesEntity();
        a.setQuery("{j: {$ne: 2}, k: {$lt: 100} }");
        a.setEnabled(true);
        a.setNotes("Some notes here");
        a.setInterval(0L);
        a.setUser(u_dao.getUserById(2L));
        Calendar c = Calendar.getInstance();
        a.setStartDate(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 10);
        a.setEndDate(c.getTime());
        Long id = dao.createAlertQuery(a);
        assertNotNull("An id for the new alert query has been created", id);
        assertEquals("The id is equal to 4", new Long(4), id);
    }
}
