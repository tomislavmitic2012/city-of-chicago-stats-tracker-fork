package com.depaul.edu.se491.service;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.resource.user.User;
import com.depaul.edu.se491.service.user.UserService;
import com.depaul.edu.se491.service.user.UserServiceImpl;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by adampodraza on 2/23/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final String USER_UUID = "some uuid";
    private static final String USER_EMAIL = "some email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FIRST_NAME = "first";
    private static final String USER_LAST_NAME = "last";
    private static boolean USER_ENABLED = true;


    UserService userService;

    @Mock
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();

        userService.setUserDao(userDao);
    }

    @Test
    public void testCreateUser() throws AppException {
        when(userDao.getUserById(Long.valueOf(1))).thenReturn(null);
        when(userDao.createUser(any(UserEntity.class))).thenReturn(Long.valueOf(1));

        User user = new User();

        user.setUuid(USER_UUID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setEnabled(USER_ENABLED);

        Long createUser = userService.createUser(user);

        verify(userDao).getUserByUuid(USER_UUID); //ensures that getUserByUuid was called in the createUser method

        verify(userDao, times(1)).getUserByEmail(USER_EMAIL);

        verify(userDao, never()).getUserById(anyLong());

        verify(userDao, times(1)).createUser(any(UserEntity.class));


        Assert.assertSame(Long.valueOf(1), createUser); //ensure that the podcast was created with id of 1
    }

    @Test(expected=AppException.class)
    public void testCreateUserError() throws AppException {
        UserEntity existingUser = new UserEntity();
        when(userDao.getUserByEmail(USER_EMAIL)).thenReturn(existingUser);

        User user = new User();
        user.setUuid(USER_UUID);
        user.setEmail(USER_EMAIL);

        userService.createUser(user);

    }

    @Test
    public void testCreateUserWithValidation_missingEmail() throws AppException {
        exception.expect(AppException.class);
        exception.expectMessage("Provided data not sufficient for insertion");

        User user = new User();

        user.setUuid(USER_UUID);
        user.setFirstName(USER_FIRST_NAME);
        user.setPassword(USER_PASSWORD);

        userService.createUser(user);

    }

    @Test
    public void testCreateUserWithValidation_missingPassword() throws AppException {
        exception.expect(AppException.class);
        exception.expectMessage("Provided data not sufficient for insertion");

        User user = new User();

        user.setUuid(USER_UUID);
        user.setFirstName(USER_FIRST_NAME);
        user.setEmail(USER_EMAIL);

        userService.createUser(user);

    }

    @Test
    public void testCreateUserWithValidation_missingFirstName() throws AppException {
        exception.expect(AppException.class);
        exception.expectMessage("Provided data not sufficient for insertion");

        User user = new User();

        user.setUuid(USER_UUID);
        user.setPassword(USER_PASSWORD);
        user.setEmail(USER_EMAIL);

        userService.createUser(user);

    }

    @Test
    public void testCreateUserWithValidation_missingUuid() throws AppException {
        exception.expect(AppException.class);
        exception.expectMessage("Provided data not sufficient for insertion");

        User user = new User();

        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setEmail(USER_EMAIL);

        userService.createUser(user);

    }

    @Test
    public void testUpdateUserSuccessful() throws AppException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(USER_UUID);
        userEntity.setEnabled(true);
        userEntity.setEmail(USER_EMAIL);

        when(userDao.getUserByUuid(USER_UUID)).thenReturn(userEntity);
        doNothing().when(userDao).updateUser(any(UserEntity.class));

        User user = new User(userEntity);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);

        userService.updateUser(user);

        verify(userDao).getUserByUuid(USER_UUID);
        verify(userDao).updateUser(any(UserEntity.class));

        Assert.assertTrue(user.getEmail() == USER_EMAIL);

        Assert.assertTrue(user.getUuid() == USER_UUID);

        Assert.assertTrue(user.getPassword() == USER_PASSWORD);
    }

    @Test
    public void testUpdateUserUnsuccessful() throws AppException {
        when(userDao.getUserByUuid(USER_UUID)).thenReturn(null);

        User user = new User();

        user.setUuid(USER_UUID);

        try {
            userService.updateUser(user);
            Assert.fail("Should have thrown exception");
        } catch(AppException e) {
            verify(userDao).getUserByUuid(USER_UUID);
            Assert.assertEquals(e.getCode(), 409);
        }

    }

    @Test
    public void testEnableUser() throws AppException {

        UserEntity entity =  new UserEntity();
        entity.setUuid(USER_UUID);
        entity.setEmail(USER_EMAIL);
        entity.setPassword(USER_PASSWORD);
        entity.setFirstName(USER_FIRST_NAME);
        entity.setLastName(USER_LAST_NAME);
        entity.setEnabled(USER_ENABLED);

        when(userDao.getUserByUuid(USER_UUID)).thenReturn(entity);


        User user = new User();

        user.setUuid(USER_UUID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setEnabled(!USER_ENABLED);



        userService.enableUserByUuid(USER_UUID);

        verify(userDao).getUserByUuid(USER_UUID);

        verify(userDao).enableUserByUuid(USER_UUID);
    }

    @Test
    public void testDisableUser() throws AppException {

        UserEntity entity =  new UserEntity();
        entity.setUuid(USER_UUID);
        entity.setEmail(USER_EMAIL);
        entity.setPassword(USER_PASSWORD);
        entity.setFirstName(USER_FIRST_NAME);
        entity.setLastName(USER_LAST_NAME);
        entity.setEnabled(!USER_ENABLED);

        when(userDao.getUserByUuid(USER_UUID)).thenReturn(entity);

        User user = new User();
        user.setUuid(USER_UUID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setEnabled(USER_ENABLED);

        userService.disableUserByUuid(USER_UUID);

        verify(userDao, times(1)).getUserByUuid(USER_UUID);
    }

}
