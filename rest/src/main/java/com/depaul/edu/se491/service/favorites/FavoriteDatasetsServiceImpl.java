package com.depaul.edu.se491.service.favorites;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsDao;
import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsEntity;
import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.errorhandling.AppException;
import com.depaul.edu.se491.resource.favorites.FavoriteDatasets;
import com.depaul.edu.se491.resource.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public void updateFavoriteDatasets(FavoriteDatasets fde)throws AppException {
        if (favoriteDatasetDao.getFavoriteDatasetsById(fde.getId()) != null) {
            favoriteDatasetDao.updateFavoriteDatasets(getEnityfromFde(fde));
        }else{
            //If user doesn't exist in database, do something else
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 409, "FavoriteDatasets doesn't exist",
                    "Please verify that FavoriteDatasets exists.", "/updatepage");
        }
    }

    @Override
    public Long createFavoriteDatasets(FavoriteDatasets fde)throws AppException {

        validateInputForCreation(fde);
        insertUserId(fde);

        FavoriteDatasetsEntity fdeEntity = getEnityfromFde(fde);
        fdeEntity = favoriteDatasetDao.getFavoriteDatasetsById(fdeEntity.getId());

        if (isFDEEntityEmpty(fdeEntity)) {

            throw new AppException(Response.Status.CONFLICT.getStatusCode(),409,"favoriteDataset already exists",
                    "Please verify that the favoriteDataset does not already exist.",
                    "/loginpage");
        }

        FavoriteDatasetsEntity entity = new FavoriteDatasetsEntity(fde);
        Long id = favoriteDatasetDao.createFavoriteDatasets(entity);

        return id;
    }

    private void validateInputForCreation(FavoriteDatasets fdeEntity) throws AppException {
        if(fdeEntity.getNotes() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the Notes is properly generated/set", "/loginpage");
        }
        if(fdeEntity.getQuery() == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
                    "Please verify that the Query is properly generated/set", "/loginpage");
        }
        
    }

    private boolean isFDEEntityEmpty(FavoriteDatasetsEntity fde) {
        return fde.getId() != null && fde.getCreatedDate() != null &&
                fde.getNotes() != null && fde.getQuery() != null &&
                fde.getUser() != null;
    }

    private void insertUserId(FavoriteDatasets fdeEntity) {
        Authentication securityAuth = SecurityContextHolder.getContext().getAuthentication();
        fdeEntity.setUserEntity((UserEntity) securityAuth.getPrincipal());
    }

    @Override
    public void setFavoriteDatasetsDao(FavoriteDatasetsDao fdeDao) {
        this.favoriteDatasetDao = fdeDao;
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
