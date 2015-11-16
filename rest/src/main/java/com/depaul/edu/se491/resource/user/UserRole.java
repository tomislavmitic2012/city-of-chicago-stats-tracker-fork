package com.depaul.edu.se491.resource.user;

import com.depaul.edu.se491.dao.user.UserRoleEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Tom Mitic on 4/20/2015.
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRole {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "role")
    private String role;

    @XmlElement(name = "user_name")
    private String userName;

    @XmlElement(name = "userId")
    private Long userId;

    public UserRole(String role, String userName, Long userId) {
        this.setRole(role);
        this.setUserName(userName);
        this.setUserId(userId);
    }

    public UserRole(UserRoleEntity userRoleEntity) {
        this.setId(userRoleEntity.getId());
        this.setRole(userRoleEntity.getRole());
        this.setUserName(userRoleEntity.getUser().getUsername());
        this.setUserId(userRoleEntity.getUser().getId());
    }

    public UserRole() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
