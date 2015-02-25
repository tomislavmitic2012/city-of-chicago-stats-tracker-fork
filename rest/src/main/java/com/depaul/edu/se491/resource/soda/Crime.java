package com.depaul.edu.se491.resource.soda;


import java.util.List;
import com.sun.jersey.api.client.GenericType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.Date;

/**
 * Created by adampodraza on 2/4/15.
 *
 * Basic POJO for Crimes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Crime {
    public static final GenericType<List<Crime>> LIST_TYPE = new GenericType<List<Crime>>() {};

    private String id;
    private String case_number;
    private Date date;
    private String block;
    private String iucr;
    private String primary_type;
    private String description;
    private String location_description;
    private String arrest;
    private String domestic;
    private String beat;
    private String district;
    private String ward;
    private String updated_on;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCase_number() {
        return case_number;
    }
    public void setCase_number(String case_number) {
        this.case_number = case_number;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }
    public String getIucr() {
        return iucr;
    }
    public void setIucr(String iucr) {
        this.iucr = iucr;
    }
    public String getPrimary_type() {
        return primary_type;
    }
    public void setPrimary_type(String primary_type) {
        this.primary_type = primary_type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation_description() {
        return location_description;
    }
    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }
    public String getArrest() {
        return arrest;
    }
    public void setArrest(String arrest) {
        this.arrest = arrest;
    }
    public String getDomestic() {
        return domestic;
    }
    public void setDomestic(String domestic) {
        this.domestic = domestic;
    }
    public String getBeat() {
        return beat;
    }
    public void setBeat(String beat) {
        this.beat = beat;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getWard() {
        return ward;
    }
    public void setWard(String ward) {
        this.ward = ward;
    }
    public String getUpdated_on() {
        return updated_on;
    }
    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }




}