package com.depaul.edu.se491.dao.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Mitic on 2/14/15.
 */
public class UserRoleDaoImpl implements UserRoleDao {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

    @PersistenceContext(unitName = "cityOfChicagoStatsPersistence")
    private EntityManager entityManager;

    @Override
    public List<UserRoleEntity> getUserRolesByUserId(Long id) {
        try {
            StringBuilder q = new StringBuilder("select u_r from UserRoleEntity u_r where u_r.user.id = ").append(id);
            TypedQuery<UserRoleEntity> qu = entityManager.createQuery(q.toString(), UserRoleEntity.class);

            return qu.getResultList();
        } catch (Throwable x) {
            logger.error(String.format("Unable to get user roles for user with id %s. Underlying Exception: %s", id, x));
            return new ArrayList<>();
        }
    }
}
