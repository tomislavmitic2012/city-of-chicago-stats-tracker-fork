package com.depaul.edu.se491.dao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Tom Mitic on 2/14/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class UserDaoImplTest {

    @Autowired
    protected UserDao dao;

    @Test
    public void getUsersTest() {
        List<UserEntity> u_s = dao.getUsers(true);
        assertNotNull(u_s);
        assertEquals("The length of the list is two", 2, u_s.size());
        assertNotEquals("The ordering of the list does not have Lisa George first", "George", u_s.get(0).getLastName());
    }

    @Test
    public void getUserByIdTest() {
        UserEntity u = dao.getUserById(2L);
        assertEquals("It should be Lisa George", "George", u.getLastName());

        u = dao.getUserById(500000L);
        assertNotNull("A null value should not be returned", u);
        assertEquals("It should be an empty user with a null user id", null, u.getId());
    }

    @Test
    public void getUserByUuidTest() {
        UserEntity u = dao.getUserByUuid("54947df8-0e9e-4471-a2f9-9af509fb5889");
        assertEquals("It should be Lisa George", "George", u.getLastName());

        u = dao.getUserByUuid("54947df8-0e9e-4471-a2f9-1dz509fb5889");
        assertNotNull("A null value should not be returned", u);
        assertEquals("It should be an empty user with a null user id", null, u.getId());
    }

    @Test
    public void getUserByEmailTest() {
        UserEntity u = dao.getUserByEmail("lisa@yahoo.com");
        assertEquals("It should be Lisa George", "George", u.getLastName());

        u = dao.getUserByUuid("tom@gmail.com");
        assertNotNull("A null value should not be returned", u);
        assertEquals("It should be an empty user with a null user id", null, u.getId());
    }

    @Test
    public void disableUserByIdTest() {
        dao.disableUserById(1L);
        UserEntity u = dao.getUserById(1L);
        assertEquals("O'Mally is now disabled", false, u.getEnabled());

        u = dao.getUserById(2L);
        assertNotEquals("George is still enabled", false, u.getEnabled());
    }

    @Test
    public void disableAllUsersTest() {
        dao.disableAllUsers();
        UserEntity u = dao.getUserById(1L);
        assertEquals("O'Mally is now disabled", false, u.getEnabled());

        u = dao.getUserById(2L);
        assertNotEquals("George is not enabled", true, u.getEnabled());
    }

    @Test
    public void disableUserByUuidTest() {
        dao.disableUserByUuid("54947df8-0e9e-4471-a2f9-9af509fb5889");
        UserEntity u = dao.getUserByUuid("54947df8-0e9e-4471-a2f9-9af509fb5889");
        assertEquals("George should be disabled", false, u.getEnabled());

        u = dao.getUserByUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        assertNotEquals("O'Mally should not be disabled", false, u.getEnabled());
    }

    @Test
    public void enableAllUsersTest() {
        dao.disableAllUsers();
        UserEntity u = dao.getUserByUuid("54947df8-0e9e-4471-a2f9-9af509fb5889");
        assertEquals("George should be disabled", false, u.getEnabled());

        u = dao.getUserByUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        assertNotEquals("O'Mally should be disabled", true, u.getEnabled());

        dao.enableAllUsers();
        u = dao.getUserByUuid("54947df8-0e9e-4471-a2f9-9af509fb5889");
        assertNotEquals("George should not be disabled", false, u.getEnabled());

        u = dao.getUserByUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        assertNotEquals("O'Mally should not be disabled", false, u.getEnabled());
    }

    @Test
    public void enableUserByIdTest() {
        dao.disableUserById(1L);
        UserEntity u = dao.getUserById(1L);
        assertEquals("O'Mally is now disabled", false, u.getEnabled());

        dao.enableUserById(1L);
        u = dao.getUserById(1L);
        assertNotEquals("O'Mally is not disabled", false, u.getEnabled());
    }

    @Test
    public void enableUserByUuidTest() {
        dao.disableUserByUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        UserEntity u = dao.getUserById(1L);
        assertEquals("O'Mally is now disabled", false, u.getEnabled());

        dao.enableUserByUuid("067e6162-3b6f-4ae2-a171-2470b63dff00");
        u = dao.getUserById(1L);
        assertNotEquals("O'Mally is not disabled", false, u.getEnabled());
    }

    @Test
    public void updateUserTest() {
        UserEntity u = dao.getUserById(1L);
        u.setLastName("McMally");
        dao.updateUser(u);
        u = dao.getUserById(1L);
        assertNotEquals("O'Mally is now McMally", "O'Mally", u.getLastName());
    }

    @Test
    public void createUserTest() {
        UserEntity u = new UserEntity();
        u.setEnabled(true);
        u.setLastName("McCreationTest");
        u.setFirstName("Testy");
        u.setEmail("testy@gmail.com");
        u.setPassword("password1");
        u.setUuid(UUID.randomUUID().toString());
        u.setCreatedDate(new Date());
        Long id = dao.createUser(u);
        assertNotNull("An id for the new user has been created", id);
        assertEquals("The id is equal to 3", new Long(3), id);
    }
}
