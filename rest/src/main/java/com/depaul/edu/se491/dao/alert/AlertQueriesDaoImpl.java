package com.depaul.edu.se491.dao.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static com.depaul.edu.se491.global.AppConstants.MAX_RESULTS;

/**
 * Created by tomislav on 2/15/15.
 */
public class AlertQueriesDaoImpl implements AlertQueriesDao {

    private static final Logger logger = LoggerFactory.getLogger(AlertQueriesDaoImpl.class);

    @PersistenceContext(unitName = "cityOfChicagoStatsPersistence")
    private EntityManager entityManager;

    @Override
    public List<AlertQueriesEntity> getAlertQueriesByUserId(Long id) {
        try {
            StringBuilder q = new StringBuilder("Select aq from AlertQueriesEntity aq where aq.user.id = ").append(id)
                    .append(" order by id desc");
            TypedQuery<AlertQueriesEntity> qu = entityManager.createQuery(q.toString(), AlertQueriesEntity.class);
            qu.setMaxResults(MAX_RESULTS);
            return qu.getResultList();
        } catch(Exception e) {
            logger.error(String.format("Error fetching alert queries for user id: %s, exception thrown: %s", id, e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<AlertQueriesEntity> getAlertQueriesByUserUuid(String uuid) {
        try {
            StringBuilder q = new StringBuilder("Select aq from AlertQueriesEntity aq where aq.user.uuid = ?1")
                    .append(" order by id desc");
            TypedQuery<AlertQueriesEntity> qu = entityManager.createQuery(q.toString(), AlertQueriesEntity.class)
                    .setParameter(1, uuid);
            qu.setMaxResults(MAX_RESULTS);
            return qu.getResultList();
        } catch(Exception e) {
            logger.error(String.format("Error fetching alert queries for user uuid: %s, exception thrown: %s", uuid, e));
            return new ArrayList<>();
        }
    }

    @Override
    public AlertQueriesEntity getAlertQueryById(Long id) {
        try {
            StringBuilder q = new StringBuilder("Select aq from AlertQueriesEntity aq where aq.id = ").append(id)
                    .append(" order by id desc");
            TypedQuery<AlertQueriesEntity> qu = entityManager.createQuery(q.toString(), AlertQueriesEntity.class);
            return qu.getSingleResult();
        } catch (Exception e) {
            logger.error(String.format("Error fetching alert queries for user id: %s, exception thrown: %s", id, e));
            return new AlertQueriesEntity();
        }
    }

    @Override
    @Transactional
    public void disableAlertQuery(Long id) {
        StringBuilder q = new StringBuilder("Select aq from AlertQueriesEntity aq where aq.id = ").append(id);
        TypedQuery<AlertQueriesEntity> qu = entityManager.createQuery(q.toString(), AlertQueriesEntity.class);
        AlertQueriesEntity aq = qu.getSingleResult();
        aq.setEnabled(false);
        entityManager.merge(aq);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void enableAlertQuery(Long id) {
        StringBuilder q = new StringBuilder("Select aq from AlertQueriesEntity aq where aq.id = ").append(id);
        TypedQuery<AlertQueriesEntity> qu = entityManager.createQuery(q.toString(), AlertQueriesEntity.class);
        AlertQueriesEntity aq = qu.getSingleResult();
        aq.setEnabled(true);
        entityManager.merge(aq);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void updateAlertQuery(AlertQueriesEntity aqe) {
        entityManager.merge(aqe);
        entityManager.flush();
    }

    @Override
    @Transactional
    public Long createAlertQuery(AlertQueriesEntity aqe) {
        AlertQueriesEntity aq = entityManager.merge(aqe);
        entityManager.flush();
        return aq.getId();
    }
}
