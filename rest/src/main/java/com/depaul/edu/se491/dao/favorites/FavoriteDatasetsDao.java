package com.depaul.edu.se491.dao.favorites;

import java.util.List;

import com.depaul.edu.se491.errorhandling.AppException;

/**
 * Created by Tom Mitic on 2/15/15.
 */
public interface FavoriteDatasetsDao {

    /**
     * Returns the collection of the user's favorite datasets.
     *
     * @param id
     * @return List<FavoriteDatasetsEntity>
     */
    public List<FavoriteDatasetsEntity> getFavoriteDatasetsByUserId(Long id);

    /**
     * Returns the collection of the user's favorite datasets.
     *
     * @param uuid
     * @return List<FavoriteDatasetsEntity>
     */
    public List<FavoriteDatasetsEntity> getFavoriteDatasetsByUserUuid(String uuid);

    /**
     * Returns a user's data set
     *
     * @param id
     * @return FavoriteDatasetsEntity
     */
    public FavoriteDatasetsEntity getFavoriteDatasetsById(Long id);

    /**
     * Updates the user's favorite datasets.
     *
     * @param fde
     */
    public void updateFavoriteDatasets(FavoriteDatasetsEntity fde);

    /**
     * Creates a favorite dataset for the user.
     *
     * @param fde
     * @return id
     */
    public Long createFavoriteDatasets(FavoriteDatasetsEntity fde);
}
