package com.depaul.edu.se491.resource.favorites;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.beanutils.BeanUtils;

import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsEntity;
import com.depaul.edu.se491.dao.user.UserEntity;


/**
 * Created by Tu Vo on 2/25/15.
 */
public class FavoriteDatasets {
	

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "user_id")
    private UserEntity user;

    @XmlElement(name = "query")
    private String query;

    @XmlElement(name = "notes")
    private String notes;

    @XmlElement(name = "created_date")
    private Date createdDate;

    
    public FavoriteDatasets(FavoriteDatasetsEntity favoriteDatasetsEntity) {
        try {
            BeanUtils.copyProperties(this, favoriteDatasetsEntity);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public FavoriteDatasets(Long id, UserEntity user, String query, String notes) {
        this.id = id;
        this.user = user;
        this.query = query;
        this.notes = notes;
    }


    public FavoriteDatasets() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getuser() {
        return user;
    }

    public void UserEntity(UserEntity user) {
        this.user = user;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
