package com.pub.lookup.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PostCode {

    @Id
    private String postCode;
    
    @OneToMany(targetEntity = Distance.class, mappedBy = "distanceId", fetch = FetchType.LAZY)
    private List<Distance> distances;

    public PostCode() {
        super();
    }

    public PostCode(String postCode, List<Distance> distances) {
        super();
        this.postCode = postCode;
        this.distances = distances;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }
    
}
