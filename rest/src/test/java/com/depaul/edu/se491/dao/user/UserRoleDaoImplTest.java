package com.depaul.edu.se491.dao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tom Mitic on 2/14/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class UserRoleDaoImplTest {

    @Autowired
    protected UserRoleDao dao;

    @Test
    public void getUserRolesByUserId() {
        List<UserRoleEntity> ures = dao.getUserRolesByUserId(1L);
        assertEquals("User with id 1 should have 2 roles", 2, ures.size());

        ures = dao.getUserRolesByUserId(2L);
        assertNotNull("User with id 2 should not return a null list of roles", ures);
        assertEquals("User with id 2 should not have any roles", 0, ures.size());
    }
}
