package com.depaul.edu.se491.service.favorites;

import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsDao;
import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsEntity;
import com.depaul.edu.se491.resource.favorites.FavoriteDatasets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tu Vo on 2/26/15.
 */
public class FavoriteDatasetsServiceImpl implements FavoriteDatasetsService {

    @Autowired
    FavoriteDatasetsDao favoriteDatasetDao;

    @Override
    public List<FavoriteDatasets> getFavoriteDatasetsByUserId(Long id) {
        List<FavoriteDatasetsEntity> entity = favoriteDatasetDao.getFavoriteDatasetsByUserId(id);

        return getFavoriteDatasetsFromEntities(entity);
    }

    @Override
    public List<FavoriteDatasets> getFavoriteDatasetsByUserUuid(String uuid) {
        List<FavoriteDatasetsEntity> entity = favoriteDatasetDao.getFavoriteDatasetsByUserUuid(uuid);

         return getFavoriteDatasetsFromEntities(entity);
    }

    @Override
    public FavoriteDatasets getFavoriteDatasetsById(Long id) {
        FavoriteDatasets favoriteDatasets = new FavoriteDatasets(favoriteDatasetDao.getFavoriteDatasetsById(id));
        return favoriteDatasets;
    }

    @Override
    public void updateFavoriteDatasets(FavoriteDatasets fde) {
        favoriteDatasetDao.updateFavoriteDatasets(getEnityfromFde(fde));
    }

    @Override
    public Long createFavoriteDatasets(FavoriteDatasets fde) {
        Long id = favoriteDatasetDao.createFavoriteDatasets(getEnityfromFde(fde));
        return id;
    }

    //Private method to marshal FavoriteDatasetsEntity  list into FavoriteDatasets list
    private List<FavoriteDatasets> getFavoriteDatasetsFromEntities(List<FavoriteDatasetsEntity> favoriteDatasetsEntities) {
        List<FavoriteDatasets> favoriteDataset = new ArrayList<FavoriteDatasets>();

        for(FavoriteDatasetsEntity favoriteDatasetEntity : favoriteDatasetsEntities) {
            favoriteDataset.add(new FavoriteDatasets(favoriteDatasetEntity));
        }

        return favoriteDataset;
    }
    
    private FavoriteDatasetsEntity getEnityfromFde(FavoriteDatasets fde){
        FavoriteDatasetsEntity fdeEnity = new FavoriteDatasetsEntity(fde);
        return fdeEnity;
    }
}
