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
    
    @Column(name = "locality")
    private String locality;
    
    @Column(name = "post_code")
    private String postCode;
    
    @OneToMany(targetEntity = Distance.class, mappedBy = "pub", 
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Distance> distances;
    
    public PubEntity() {
        super();
    }

    public PubEntity(String pubName, String locality) {
        this.pubId = pubName + locality;
        this.pubName = pubName;
        this.locality = locality;
        setDistances(new ArrayList<Distance>());
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

}
