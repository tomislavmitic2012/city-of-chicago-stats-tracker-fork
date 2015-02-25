package com.depaul.edu.se491.resource.soda;


import com.sun.jersey.api.client.GenericType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import java.util.Date;
import java.util.List;


/**
 * Created by T. Mitic on 1/22/15.
 *
 * FoodInspection POJO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodInspection {
    public static final GenericType<List<FoodInspection>> LIST_TYPE = new GenericType<List<FoodInspection>>() {};

    private Long inspection_id;
    private String dba_name;
    private String aka_name;
    private Long license_;
    private String facility_type;
    private String risk;
    private String address;
    private String city;
    private String state;
    private Long zip;
    private Date inspection_date;
    private String violations;
    private String inspection_type;
    private String results;
    private String latitude;
    private String longitude;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getInspection_type() {
        return inspection_type;
    }

    public void setInspection_type(String inspection_type) {
        this.inspection_type = inspection_type;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
        this.violations = violations;
    }

    public Date getInspection_date() {
        return inspection_date;
    }

    public void setInspection_date(Date inspection_date) {
        this.inspection_date = inspection_date;
    }

    public Long getInspection_id() {
        return inspection_id;
    }

    public void setInspection_id(Long inspection_id) {
        this.inspection_id = inspection_id;
    }

    public String getDba_name() {
        return dba_name;
    }

    public void setDba_name(String dba_name) {
        this.dba_name = dba_name;
    }

    public String getAka_name() {
        return aka_name;
    }

    public void setAka_name(String aka_name) {
        this.aka_name = aka_name;
    }

    public Long getLicense_() {
        return license_;
    }

    public void setLicense_(Long license_) {
        this.license_ = license_;
    }

    public String getFacility_type() {
        return facility_type;
    }

    public void setFacility_type(String facility_type) {
        this.facility_type = facility_type;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }





}
