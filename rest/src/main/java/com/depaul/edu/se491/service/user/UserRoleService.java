package com.depaul.edu.se491.service.user;

import com.depaul.edu.se491.resource.user.UserRole;

import java.util.List;

/**
 * Created by Tom Mitic on 2/16/15.
 */
public interface UserRoleService {

    /**
     * Returns the user roles within the system got a particular user id
     *
     * @param id
     * @return List<UserRoleEntity>
     */
    public List<UserRole> getUserRolesByUserId(Long id);
}
