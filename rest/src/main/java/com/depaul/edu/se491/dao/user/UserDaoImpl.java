package com.depaul.edu.se491.dao.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

import static com.depaul.edu.se491.global.AppConstants.MAX_RESULTS;

/**
 * Created by Tom Mitic on 2/13/15.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext(unitName = "cityOfChicagoStatsPersistence")
    private EntityManager entityManager;

    @Override
    public List<UserEntity> getUsers(Boolean orderByInsertionDate) {
        StringBuilder q = new StringBuilder("Select u from UserEntity u");
        if (orderByInsertionDate != null && Boolean.TRUE.equals(orderByInsertionDate)) {
            q.append(" order by u.createdDate asc");
        }
        TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class);
        qu.setMaxResults(MAX_RESULTS);

        List<UserEntity> l = qu.getResultList();
        l.stream().forEach((u) -> u.getUserRoles());
        return l;
    }

    @Override
    public UserEntity getUserById(Long id) {
        try {
            StringBuilder q = new StringBuilder("Select u from UserEntity u where u.id = ").append(id);
            TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class);

            UserEntity u = qu.getSingleResult();
            u.getUserRoles();
            return u;
        } catch (Exception x) {
            logger.error(String.format("Unable to get user with id %s. Underlying Exception: %s", id, x));
            return new UserEntity();
        }
    }

    @Override
    public UserEntity getUserByUuid(String uuid) {
        try {
            StringBuilder q = new StringBuilder("Select u from UserEntity u where u.uuid = ?1");
            TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class).setParameter(1, uuid);

            UserEntity u = qu.getSingleResult();
            u.getUserRoles();
            return u;
        } catch (Exception x) {
            logger.error(String.format("Unable to get user with uuid %s. Underlying Exception: %s", uuid, x));
            return new UserEntity();
        }
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        try {
            StringBuilder q = new StringBuilder("Select u from UserEntity u where u.email = ?1");
            TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class).setParameter(1, email);

            UserEntity u = qu.getSingleResult();
            u.getUserRoles();
            return u;
        } catch (Exception x) {
            logger.error(String.format("Unable to get user with uuid %s. Underlying Exception: %s", email, x));
            return new UserEntity();
        }
    }

    @Override
    @Transactional
    public void disableUserById(Long id) {
        StringBuilder q = new StringBuilder("Select u from UserEntity u where u.id = ").append(id);
        TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class);
        UserEntity u = qu.getSingleResult();
        u.setEnabled(false);
        entityManager.merge(u);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void disableAllUsers() {
        List<UserEntity> uS = this.getUsers(Boolean.TRUE);
        uS.stream().forEach((u) -> {
            u.setEnabled(false);
            entityManager.merge(u);
        });
        entityManager.flush();
    }

    @Override
    @Transactional
    public void disableUserByUuid(String uuid) {
        StringBuilder q = new StringBuilder("Select u from UserEntity u where u.uuid = ?1");
        TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class).setParameter(1, uuid);
        UserEntity u = qu.getSingleResult();
        u.setEnabled(false);
        entityManager.merge(u);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void enableAllUsers() {
        List<UserEntity> uS = this.getUsers(Boolean.TRUE);
        uS.stream().forEach((u) -> {
            u.setEnabled(true);
            entityManager.merge(u);
        });
        entityManager.flush();
    }

    @Override
    @Transactional
    public void enableUserById(Long id) {
        StringBuilder q = new StringBuilder("Select u from UserEntity u where u.id = ").append(id);
        TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class);
        UserEntity u = qu.getSingleResult();
        u.setEnabled(true);
        entityManager.merge(u);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void enableUserByUuid(String uuid) {
        StringBuilder q = new StringBuilder("Select u from UserEntity u where u.uuid = ?1");
        TypedQuery<UserEntity> qu = entityManager.createQuery(q.toString(), UserEntity.class).setParameter(1, uuid);
        UserEntity u = qu.getSingleResult();
        u.setEnabled(true);
        entityManager.merge(u);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void updateUser(UserEntity userEntity) {
        entityManager.merge(userEntity);
        entityManager.flush();
    }

    @Override
    @Transactional
    public Long createUser(UserEntity userEntity) {
        UserEntity u = entityManager.merge(userEntity);
        entityManager.flush();
        return u.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserByEmail(email);
    }
}
