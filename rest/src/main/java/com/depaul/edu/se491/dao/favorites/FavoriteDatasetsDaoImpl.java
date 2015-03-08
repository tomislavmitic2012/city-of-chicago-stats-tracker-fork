package com.depaul.edu.se491.dao.favorites;

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
 * Created by Tom Mitic on 2/15/15.
 */
public class FavoriteDatasetsDaoImpl implements FavoriteDatasetsDao {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteDatasetsDaoImpl.class);

    @PersistenceContext(unitName = "cityOfChicagoStatsPersistence")
    private EntityManager entityManager;
    
    @Override
    public List<FavoriteDatasetsEntity> getFavoriteDatasetsByUserId(Long id) {
        try {
            StringBuilder q = new StringBuilder("select f from FavoriteDatasetsEntity f where f.user.id = ")
                    .append(id).append(" order by f.createdDate asc");
            TypedQuery<FavoriteDatasetsEntity> qu = entityManager.createQuery(q.toString(), FavoriteDatasetsEntity.class);
            qu.setMaxResults(MAX_RESULTS);
            return qu.getResultList();
        } catch(Exception e) {
            logger.error(String.format("Error fetching favorite data sets for user id: %s, exception thrown: %s", id, e));
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<FavoriteDatasetsEntity> getFavoriteDatasetsByUserUuid(String uuid) {
        try {
            StringBuilder q = new StringBuilder("select f from FavoriteDatasetsEntity f where f.user.uuid = ?1")
                    .append(" order by f.createdDate asc");
            TypedQuery<FavoriteDatasetsEntity> qu = entityManager.createQuery(q.toString(), FavoriteDatasetsEntity.class)
                    .setParameter(1, uuid);
            qu.setMaxResults(MAX_RESULTS);

            return qu.getResultList();
        } catch(Exception e) {
            logger.error(String.format("Error fetching favorite data sets for user uuid: %s, exception thrown: %s", uuid, e));
            return new ArrayList<>();
        }
    }

    @Override
    public FavoriteDatasetsEntity getFavoriteDatasetsById(Long id) {
        try {
            StringBuilder q = new StringBuilder("select f from FavoriteDatasetsEntity f where f.id = ")
                    .append(id);
            TypedQuery<FavoriteDatasetsEntity> qu = entityManager.createQuery(q.toString(), FavoriteDatasetsEntity.class);

            return qu.getSingleResult();
        } catch (Exception e) {
            logger.error(String.format("Error fetching favorite data sets for favorite datasets id: %s, exception thrown: %s", id, e));
            return new FavoriteDatasetsEntity();
        }
    }

    @Override
    @Transactional
    public void updateFavoriteDatasets(FavoriteDatasetsEntity fde) {
        entityManager.merge(fde);
        entityManager.flush();
    }

    @Override
    @Transactional
    public Long createFavoriteDatasets(FavoriteDatasetsEntity fde) {
        FavoriteDatasetsEntity f = entityManager.merge(fde);
        entityManager.flush();
        return f.getId();
    }
}
