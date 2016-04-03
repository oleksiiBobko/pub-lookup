package com.pub.lookup.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PubEntity {

    @Id
    private String pubId;
    
    @Column
    private String pubName;
    
    @Column
    private String locality;
    
    @OneToMany(targetEntity = Distance.class, mappedBy = "distanceId", fetch = FetchType.LAZY)
    private List<Distance> distances;
    
    public PubEntity(String pubName, String locality) {
        this.pubName = pubName;
        this.locality = locality;
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

}
