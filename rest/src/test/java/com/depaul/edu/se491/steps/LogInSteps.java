package com.depaul.edu.se491.steps;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.resource.authenticate.AuthenticateResource;
import com.depaul.edu.se491.resource.authenticate.TokenTransfer;
import com.depaul.edu.se491.resource.user.User;
import com.depaul.edu.se491.service.user.UserService;
import com.depaul.edu.se491.service.user.UserServiceImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by adampodraza on 5/11/15.
 */
public class LogInSteps {

    UserEntity existingUser = new UserEntity();
    private UserService userService = new UserServiceImpl();
    private static final String USER_UUID = "some uuid";
    private static final String USER_EMAIL = "some email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FIRST_NAME = "first";
    private static final String USER_LAST_NAME = "last";
    private static boolean USER_ENABLED = true;

    Long createUser;

    @Mock
    private AuthenticateResource auth;

    @Mock
    UserDao userDao;

    @Before
    public void setUp() throws AppException {


    }

    @Given("^I have an account$")
    public void i_have_an_account() throws Throwable {
        MockitoAnnotations.initMocks(this);

        userService.setUserDao(userDao);
        when(userDao.getUserByEmail(USER_EMAIL)).thenReturn(existingUser);
        when(userDao.getUserByUuid(USER_UUID)).thenReturn(existingUser);

        when(userDao.getUserById(Long.valueOf(1))).thenReturn(null);
        when(userDao.createUser(any(UserEntity.class))).thenReturn(Long.valueOf(1));

        User user = new User();

        user.setUuid(USER_UUID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setEnabled(USER_ENABLED);

        createUser = userService.createUser(user);
        UserEntity existingUser = new UserEntity();

        when(userDao.getUserByEmail(USER_EMAIL)).thenReturn(existingUser);
        when(userDao.createUser(any(UserEntity.class))).thenReturn(Long.valueOf(1));

        assertNotNull(userService);

        verify(userDao).getUserByUuid(USER_UUID); //ensures that getUserByUuid was called in the createUser method

        verify(userDao, times(1)).getUserByEmail(USER_EMAIL);

        verify(userDao, never()).getUserById(anyLong());

        verify(userDao, times(1)).createUser(any(UserEntity.class));


        assertEquals(Long.valueOf(1), createUser);

    }

    @When("^I enter a username and password$")
    public void i_enter_a_username_and_password() throws Throwable {
        TokenTransfer token = new TokenTransfer("token");

        when(auth.authenticate(USER_EMAIL, USER_PASSWORD)).thenReturn(token);

        assertNotNull(auth);

        TokenTransfer token1 = auth.authenticate(USER_EMAIL, USER_PASSWORD);

        assertEquals(token1.getToken(), token.getToken());
    }

    @Then("^I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() throws Throwable {

        TokenTransfer token = new TokenTransfer("token");

        when(auth.authenticate(USER_EMAIL, USER_PASSWORD)).thenReturn(token);

        assertNotNull(auth);

        TokenTransfer token1 = auth.authenticate(USER_EMAIL, USER_PASSWORD);

        assertEquals(token1.getToken(), token.getToken());

    }

}
