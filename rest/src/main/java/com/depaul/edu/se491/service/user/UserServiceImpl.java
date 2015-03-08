package com.depaul.edu.se491.service.user;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.resource.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Mitic on 2/17/15.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUsers(Boolean orderByInsertionDate) {

        List<UserEntity> entities = userDao.getUsers(orderByInsertionDate);

        return getUsersFromEntities(entities);
    }

    @Override
    public User getUserById(Long id) {

        User userById = new User(userDao.getUserById(id));

        return userById;
    }

    @Override
    public User getUserByUuid(String uuid) {

        User userByUuid = new User(userDao.getUserByUuid(uuid));

        return userByUuid;
    }

    @Override
    public User getUserByEmail(String email) {

        User userByEmail = new User(userDao.getUserByEmail(email));

        return userByEmail;
    }

    @Override
    @Transactional("transactionManager")
    public void disableUserById(Long id) throws AppException{

        userDao.disableUserById(id);

    }

    @Override
    @Transactional("transactionManager")
    public void disableAllUsers() throws AppException {

        userDao.disableAllUsers();

    }

    @Override
    @Transactional("transactionManager")
    public void disableUserByUuid(String uuid) throws AppException{

        if(userDao.getUserByUuid(uuid) == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 409, "User doesn't exist",
                    "Please verify that user exists.", "/updatepage");
        }

        userDao.disableUserByUuid(uuid);

    }

    @Override
    @Transactional("transactionManager")
    public void enableAllUsers() throws AppException{

        userDao.enableAllUsers();

    }

    @Override
    @Transactional("transactionManager")
    public void enableUserById(Long id) throws AppException{

        userDao.enableUserById(id);

    }

    @Override
    @Transactional("transactionManager")
    public void enableUserByUuid(String uuid) throws AppException {

        if(userDao.getUserByUuid(uuid) == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 409, "User doesn't exist",
                    "Please verify that user exists.", "/updatepage");
        }

        userDao.enableUserByUuid(uuid);

    }

    @Override
    @Transactional("transactionManager")
    public void updateUser(User userEntity) throws AppException{

        if(userDao.getUserByUuid(userEntity.getUuid()) != null || userDao.getUserByEmail(userEntity.getEmail()) != null) {

            UserEntity entity = getEntityFromUser(userEntity);

            userDao.updateUser(entity);

        }

        else {
            //If user doesn't exist in database, do something else
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 409, "User doesn't exist",
                    "Please verify that user exists.", "/updatepage");
        }
    }

    @Override
    @Transactional("transactionManager")
    public Long createUser(User userEntity) throws AppException {

        UserEntity userEntity1 = userDao.getUserByUuid(userEntity.getUuid());

        UserEntity userEntity2 = userDao.getUserByEmail(userEntity.getEmail());

        if(userEntity1 != null || userEntity2 != null) {
            throw new AppException(Response.Status.CONFLICT.getStatusCode(), 409, "User already exists",
                    "Please verify that the user does not already exist.", "/loginpage");
        }

        validateInputForCreation(userEntity);

        UserEntity entity = new UserEntity(userEntity);

        Long id = userDao.createUser(entity);

        return id;
    }

    private void validateInputForCreation(User userEntity) throws AppException {

        if(userEntity.getEmail() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the email is properly generated/set", "/loginpage");
        }

        if(userEntity.getPassword() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the password is properly generated/set", "/loginpage");
        }

        if(userEntity.getFirstName() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the first name is properly generated/set", "/loginpage");
        }

        if(userEntity.getUuid() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the uuid is properly generated/set", "/loginpage");
        }
    }

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //Private method to marshal user entity list into user list
    private List<User> getUsersFromEntities(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<User>();

        for(UserEntity userEntity : userEntities) {
            users.add(new User(userEntity));
        }

        return users;
    }

    private UserEntity getEntityFromUser(User user) {

        UserEntity userEntity = new UserEntity(user);

        return userEntity;
    }
}
