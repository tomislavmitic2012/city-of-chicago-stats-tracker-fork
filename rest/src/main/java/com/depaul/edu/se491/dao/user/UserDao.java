package com.depaul.edu.se491.dao.user;

import java.util.List;

/**
 * Created by Tomislav S. Mitic on 2/13/15.
 */
public interface UserDao {

    /**
     * Get all users in the system. Limit 50
     *
     * @param orderByInsertionDate
     * @return List<UserEntity>
     */
    public List<UserEntity> getUsers(Boolean orderByInsertionDate);

    /**
     * Returns a user based on his/her id
     *
     * @param id
     * @return UserEntity
     */
    public UserEntity getUserById(Long id);

    /**
     * Returns the user based on his/her uuid
     *
     * @param uuid
     * @return UserEntity
     */
    public UserEntity getUserByUuid(String uuid);

    /**
     * Returns the user based on his/her email
     *
     * @param email
     * @return UserEntity
     */
    public UserEntity getUserByEmail(String email);

    /**
     * The user's enabled flag is set to false
     *
     * @param id
     */
    public void disableUserById(Long id);

    /**
     * Disable all users
     */
    public void disableAllUsers();

    /**
     * The user's enabled flag is set to false
     *
     * @param uuid
     */
    public void disableUserByUuid(String uuid);

    /**
     * Enable all users
     */
    public void enableAllUsers();

    /**
     * The user's enabled flag is set to true
     *
     * @param id
     */
    public void enableUserById(Long id);

    /**
     * The user's enabled flag is set to true
     *
     * @param id
     */
    public void enableUserByUuid(String id);

    /**
     * Update the user
     *
     * @param userEntity
     */
    public void updateUser(UserEntity userEntity);

    /**
     * Create a user
     *
     * @param userEntity
     * @return id
     */
    public Long createUser(UserEntity userEntity);
}
