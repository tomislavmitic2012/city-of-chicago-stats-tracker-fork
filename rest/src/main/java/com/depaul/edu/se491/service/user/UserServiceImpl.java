package com.depaul.edu.se491.service.user;

import com.depaul.edu.se491.dao.user.UserDao;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.resource.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    public void disableUserById(Long id) {

        userDao.disableUserById(id);

    }

    @Override
    @Transactional("transactionManager")
    public void disableAllUsers() {

        userDao.disableAllUsers();

    }

    @Override
    @Transactional("transactionManager")
    public void disableUserByUuid(String uuid) {

        userDao.disableUserByUuid(uuid);

    }

    @Override
    @Transactional("transactionManager")
    public void enableAllUsers() {

        userDao.enableAllUsers();

    }

    @Override
    @Transactional("transactionManager")
    public void enableUserById(Long id) {

        userDao.enableUserById(id);

    }

    @Override
    @Transactional("transactionManager")
    public void enableUserByUuid(String id) {

        userDao.enableUserByUuid(id);

    }

    @Override
    @Transactional("transactionManager")
    public void updateUser(User userEntity) {

        if(userDao.getUserByUuid(userEntity.getUuid()) != null) {

            UserEntity entity = getEntityFromUser(userEntity);

            userDao.updateUser(entity);

        }

        else {
            //If user doesn't exist in database, do something else
        }
    }

    @Override
    @Transactional("transactionManager")
    public Long createUser(User userEntity) {

        UserEntity entity = new UserEntity(userEntity);

        Long id = userDao.createUser(entity);

        return id;
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
