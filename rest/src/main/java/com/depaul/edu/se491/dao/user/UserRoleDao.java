package com.depaul.edu.se491.dao.user;

import java.util.List;

/**
 * Created by Tom Mitic on 2/14/15.
 */
public interface UserRoleDao {

    /**
     * Returns the user roles within the system got a particular user id
     *
     * @param id
     * @return List<UserRoleEntity>
     */
    public List<UserRoleEntity> getUserRolesByUserId(Long id);

    /**
     * Adds the specific role for the user with the specified user id within the userRoleEntity
     *
     * @param userRoleEntity
     * @return the id of the user that the role was added to
     */
    public Long addUserRole(UserRoleEntity userRoleEntity);
}
