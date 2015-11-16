package com.depaul.edu.se491.resource.favorites;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;

import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsEntity;
import com.depaul.edu.se491.dao.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Tu Vo on 2/25/15.
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FavoriteDatasets implements Serializable {

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

    private static final Logger logger = LoggerFactory.getLogger(FavoriteDatasets.class);

    
    public FavoriteDatasets(FavoriteDatasetsEntity favoriteDatasetsEntity) {
        try {
            BeanUtils.copyProperties(this, favoriteDatasetsEntity);
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException", e);

        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);

        }
    }

    public FavoriteDatasets(Long id, UserEntity user, String query, String notes) {
        this.id = id;
        this.user = user;
        this.query = query;
        this.notes = notes;
    }


    public FavoriteDatasets() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUserEntity(UserEntity user) {
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
