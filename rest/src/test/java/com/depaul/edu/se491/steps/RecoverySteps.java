package com.depaul.edu.se491.steps;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.resource.authenticate.AuthenticateResource;
import com.depaul.edu.se491.resource.user.User;
import com.depaul.edu.se491.service.user.UserService;
import com.depaul.edu.se491.service.user.UserServiceImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by adampodraza on 5/12/15.
 */
public class RecoverySteps {

    UserEntity existingUser = new UserEntity();
    private UserService userService = new UserServiceImpl();
    private static final String USER_UUID = "some uuid";
    private static final String USER_EMAIL = "some email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FIRST_NAME = "first";
    private static final String USER_LAST_NAME = "last";
    private static final String USER_QUESTION = "some question";
    private static boolean USER_ENABLED = true;

    Long createUser;

    @Mock
    private AuthenticateResource auth;

    @Mock
    UserDao userDao;

    @Before
    public void setUp() {

    }

    @Given("^I already have an account$")
    public void i_already_have_an_account() throws Throwable {
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

    @When("^I try to recover my password$")
    public void i_try_to_recover_my_password() throws Throwable {
        User user = new User();

        user.setUuid(USER_UUID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setEnabled(USER_ENABLED);

        when(auth.restPassword(USER_UUID, USER_QUESTION, USER_PASSWORD)).thenReturn(user);

        User user1 = auth.restPassword(USER_UUID, USER_QUESTION, USER_PASSWORD);

        assertEquals(user, user1);

    }

    @Then("^I should be redirected to a recovery page$")
    public void i_should_be_redirected_to_a_recovery_page() throws Throwable {

    }
}
