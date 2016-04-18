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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "geo_search")
public class GeoSearch {
    
    public GeoSearch() {
        super();
        geoResults = new ArrayList<GeoResult>();
    }

    @Id
    @NotNull
    @NotEmpty
    @Column
    private String searchId;
    
    @OneToMany(targetEntity = GeoResult.class, mappedBy = "geoSearch", 
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GeoResult> geoResults;
    
    public List<GeoResult> getGeoResults() {
        return geoResults;
    }

    public void setGeoResults(List<GeoResult> geoResults) {
        this.geoResults = geoResults;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
    
}
