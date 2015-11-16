package com.depaul.edu.se491.resource.user;

import com.depaul.edu.se491.dao.user.UserEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;


/**
 * Created by Tom Mitic on 2/13/15.
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable{

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "password")
    private String password;

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "enabled")
    private boolean enabled;

    @XmlElement(name = "created_date")
    private Date createdDate;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(User.class);

    public User(UserEntity userEntity) {
        try {
            BeanUtils.copyProperties(this, userEntity);
        } catch (InvocationTargetException e) {
            logger.error(String.format("InvocationTargetException", e));

        } catch (IllegalAccessException e) {
            logger.error(String.format("IllegalAccessException", e));

        }
    }

    public User(String uuid, String password, String firstName, String lastName, String email) {
        this.uuid = uuid;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
