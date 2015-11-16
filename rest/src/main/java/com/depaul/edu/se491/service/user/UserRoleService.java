package com.depaul.edu.se491.service.user;

import com.depaul.edu.se491.errorhandling.AppException;
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

    /**
     * Adds a roles for the user specified in the UserRole object.
     *
     * @param userRole
     * @return the userid that the user role was added to
     */
    public Long addUserRole(UserRole userRole) throws AppException;
}
