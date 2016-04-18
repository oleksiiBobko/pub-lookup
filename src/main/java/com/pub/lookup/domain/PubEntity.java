package com.pub.lookup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pub")
public class PubEntity {

    @Id
    @Column(name = "pub_id")
    private String pubId;
    
    @Column(name = "pub_name")
    private String pubName;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "post_code")
    private String postCode;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "district")
    private String district;
    
    @OneToMany(targetEntity = Distance.class, mappedBy = "pub", 
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Distance> distances;
    
    public PubEntity() {
        super();
    }

    public PubEntity(String pubName, String city) {
        this.pubId = pubName + city;
        this.pubName = pubName;
        this.city = city;
        setDistances(new ArrayList<Distance>());
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
