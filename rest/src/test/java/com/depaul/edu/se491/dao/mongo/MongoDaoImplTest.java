package com.depaul.edu.se491.dao.mongo;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Tom Mitic on 2/28/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class MongoDaoImplTest {

    @Autowired
    MongoDao dao;

    @Test
    public void findOne() {
        DBObject o = dao.findOne("crimes");
        assertNotNull(o);

    }

    @Test
    public void find() {
        DBCursor c = dao.find("foodinspections");
        assertNotNull(c);
        assertNotEquals(0, c.toArray().size());
    }

    @Test
    public void findOneWithParameter() {
        DBObject o = dao.findOne("crimes", "{'Date' : '02/20/2015 11:56:00 PM'}");
        assertNotNull(o);
        assertEquals("054XX W HADDON AVE", o.get("Block"));
    }

    @Test
    public void findWithParameter() {
        DBCursor c = dao.find("foodinspections", "{'Zip': { $gte : 60655, $lte : 60656 }}");
        assertNotNull(c);
        assertNotEquals(0, c.toArray().size());
    }
}
