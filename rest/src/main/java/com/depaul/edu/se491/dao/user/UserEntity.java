package com.depaul.edu.se491.dao.user;

import com.depaul.edu.se491.dao.alert.AlertQueriesEntity;
import com.depaul.edu.se491.dao.favorites.FavoriteDatasetsEntity;
import com.depaul.edu.se491.resource.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomislav S. Mitic on 2/13/15.
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

    @Column(name="uuid")
    private String uuid;

    @Column(name="password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRoleEntity> userRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<FavoriteDatasetsEntity> favoriteDatasets = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<AlertQueriesEntity> alertQueries = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(String firstName, String lastName, String email, Boolean enabled, String uuid, String password) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setEnabled(enabled);
        this.createdDate = new Date();
        this.uuid = uuid;
        this.password = password;
    }

    public UserEntity(User user) {
        try {
            BeanUtils.copyProperties(this, user);
            this.createdDate = new Date();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ID of the user
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * First name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Email of the user, serves as his login
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * whether of not the user is enabled within the system
     */
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Created data of the entity
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
    }

    public List<FavoriteDatasetsEntity> getFavoriteDatasets() {
        return favoriteDatasets;
    }

    public void setFavoriteDatasets(List<FavoriteDatasetsEntity> favoriteDatasets) {
        this.favoriteDatasets = favoriteDatasets;
    }

    public List<AlertQueriesEntity> getAlertQueries() {
        return alertQueries;
    }

    public void setAlertQueries(List<AlertQueriesEntity> alertQueries) {
        this.alertQueries = alertQueries;
    }
}
