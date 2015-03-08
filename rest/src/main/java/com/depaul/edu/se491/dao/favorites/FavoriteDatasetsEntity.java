package com.depaul.edu.se491.dao.favorites;

import com.depaul.edu.se491.dao.user.UserEntity;
import com.depaul.edu.se491.resource.favorites.FavoriteDatasets;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by Tom Mitic on 2/15/15.
 */
@Entity
@Table(name = "favorite_datasets")
public class FavoriteDatasetsEntity {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "query")
    private String query;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_date")
    private Date createdDate;

    public FavoriteDatasetsEntity() {}
    
    public FavoriteDatasetsEntity(FavoriteDatasets fde) {
        try {
            BeanUtils.copyProperties(this, fde);
            this.createdDate = new Date();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public FavoriteDatasetsEntity(Long id, UserEntity user, String query, String notes) {
        this.id = id;
        this.user = user;
        this.query = query;
        this.notes = notes;
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

    public void setUser(UserEntity user) {
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
