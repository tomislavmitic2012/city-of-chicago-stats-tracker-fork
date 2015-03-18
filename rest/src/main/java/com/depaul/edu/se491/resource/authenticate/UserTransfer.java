package com.depaul.edu.se491.resource.authenticate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by Tom Mitic on 3/14/15.
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserTransfer {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "roles")
    private Map<String, Boolean> roles;

    public UserTransfer(String name, Map<String, Boolean> roles) {
        this.setName(name);
        this.setRoles(roles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Boolean> roles) {
        this.roles = roles;
    }
}