package com.depaul.edu.se491.service.user;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.dao.user.UserRoleDao;
import com.depaul.edu.se491.dao.user.UserRoleEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.global.AppConstants;
import com.depaul.edu.se491.resource.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Mitic on 2/17/15.
 */
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    UserRoleDao dao;

    @Autowired
    UserDao userDao;

    @Override
    public List<UserRole> getUserRolesByUserId(Long id) {
        return getUsersFromEntities(dao.getUserRolesByUserId(id));
    }

    @Override
    @Transactional("transactionManager")
    public Long addUserRole(UserRole userRole) throws AppException {
        UserEntity userEntity1 = userDao.getUserById(userRole.getUserId());

        UserEntity userEntity2 = userDao.getUserByEmail(userRole.getUserName());

        if(userEntity1 == null || userEntity2 == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 409, AppConstants.USER_DOESNT_EXIST,
                    AppConstants.USER_DOESNT_EXIST_MESSAGE, AppConstants.CREATEACCOUNT_END_POINT);
        }

        validateUserRole(userRole);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(userRole.getRole());
        userRoleEntity.setUser(userEntity1);

        return dao.addUserRole(userRoleEntity);
    }

    private void validateUserRole(UserRole userRole) throws AppException {
        if (userRole.getRole() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.PROVIDED_DATA_INSUFFICIENT,
                    AppConstants.VERIFY_USER_ROLE_MESSAGE, AppConstants.CREATEACCOUNT_END_POINT);
        }

        if (userRole.getUserName() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.PROVIDED_DATA_INSUFFICIENT,
                    AppConstants.VERIFY_EMAIL_MESSAGE, AppConstants.CREATEACCOUNT_END_POINT);
        }

        if (userRole.getUserId() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, AppConstants.PROVIDED_DATA_INSUFFICIENT,
                    AppConstants.VERIFY_USER_ID_MESSAGE, AppConstants.CREATEACCOUNT_END_POINT);
        }
    }

    private List<UserRole> getUsersFromEntities(List<UserRoleEntity> userRoleEntities) {
        List<UserRole> users = new ArrayList<UserRole>();

        for(UserRoleEntity userRoleEntity : userRoleEntities) {
            users.add(new UserRole(userRoleEntity));
        }

        return users;
    }
}
