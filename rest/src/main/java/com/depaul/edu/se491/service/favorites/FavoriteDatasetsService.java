package com.depaul.edu.se491.service.favorites;

import com.depaul.edu.se491.resource.favorites.FavoriteDatasets;

import java.util.List;

/**
 * Created by Tom Mitic on 2/16/15.
 */
public interface FavoriteDatasetsService {

    /**
     * Returns the collection of the user's favorite datasets.
     *
     * @param id
     * @return List<FavoriteDatasetsEntity>
     */
    public List<FavoriteDatasets> getFavoriteDatasetsByUserId(Long id);

    /**
     * Returns the collection of the user's favorite datasets.
     *
     * @param uuid
     * @return List<FavoriteDatasetsEntity>
     */
    public List<FavoriteDatasets> getFavoriteDatasetsByUserUuid(String uuid);

    /**
     * Returns a user's data set
     *
     * @param id
     * @return FavoriteDatasetsEntity
     */
    public FavoriteDatasets getFavoriteDatasetsById(Long id);

    /**
     * Updates the user's favorite datasets.
     *
     * @param fde
     */
    public void updateFavoriteDatasets(FavoriteDatasets fde);

    /**
     * Creates a favorite dataset for the user.
     *
     * @param fde
     * @return id
     */
    public Long createFavoriteDatasets(FavoriteDatasets fde);
}
