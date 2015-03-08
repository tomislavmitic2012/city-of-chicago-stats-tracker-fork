package com.depaul.edu.se491.service.user;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.resource.user.User;

import java.util.List;

/**
 * Created by Tom Mitic on 2/16/15.
 */
public interface UserService {

    /**
     * Get all users in the system. Limit 50
     *
     * @param orderByInsertionDate
     * @return List<UserEntity>
     */
    public List<User> getUsers(Boolean orderByInsertionDate);

    /**
     * Returns a user based on his/her id
     *
     * @param id
     * @return UserEntity
     */
    public User getUserById(Long id);

    /**
     * Returns the user based on his/her uuid
     *
     * @param uuid
     * @return UserEntity
     */
    public User getUserByUuid(String uuid);

    /**
     * Returns the user based on his/her email
     *
     * @param email
     * @return UserEntity
     */
    public User getUserByEmail(String email);

    /**
     * The user's enabled flag is set to false
     *
     * @param id
     */
    public void disableUserById(Long id) throws AppException;

    /**
     * Disable all users
     */
    public void disableAllUsers() throws AppException;

    /**
     * The user's enabled flag is set to false
     *
     * @param uuid
     */
    public void disableUserByUuid(String uuid) throws AppException;

    /**
     * Enable all users
     */
    public void enableAllUsers() throws AppException;

    /**
     * The user's enabled flag is set to true
     *
     * @param id
     */
    public void enableUserById(Long id) throws AppException;

    /**
     * The user's enabled flag is set to true
     *
     * @param id
     */
    public void enableUserByUuid(String id) throws AppException;

    /**
     * Update the user
     *
     * @param userEntity
     */
    public void updateUser(User userEntity) throws AppException;

    /**
     * Create a user
     *
     * @param userEntity
     * @return id
     */
    public Long createUser(User userEntity) throws AppException;

    public void setUserDao(UserDao userDao);
}
