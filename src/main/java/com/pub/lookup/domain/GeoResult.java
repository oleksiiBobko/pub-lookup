package com.pub.lookup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "geo_result")
public class GeoResult {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "search_id")
    private GeoSearch geoSearch;
    
    @Column(name = "formatted")
    private String formatted;
    
    @Column(name = "locality")
    private String locality;
    
    @Column(name = "postal_town")
    private String postalTown;
    
    @Column(name = "administrative_area_level_1")
    private String administrativeAreaLevel1;
    
    @Column(name = "administrative_area_level_2")
    private String administrativeAreaLevel2;
    
    @Column(name = "administrative_area_level_3")
    private String administrativeAreaLevel3;
    
    @Column(name = "administrative_area_level_4")
    private String administrativeAreaLevel4;
    
    @Column(name = "administrative_area_level_5")
    private String administrativeAreaLevel5;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "postal_code")
    private String postalCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GeoSearch getGeoSearch() {
        return geoSearch;
    }

    public void setGeoSearch(GeoSearch geoSearch) {
        this.geoSearch = geoSearch;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalTown() {
        return postalTown;
    }

    public void setPostalTown(String postalTown) {
        this.postalTown = postalTown;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    public void setAdministrativeAreaLevel1(String administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    public String getAdministrativeAreaLevel2() {
        return administrativeAreaLevel2;
    }

    public void setAdministrativeAreaLevel2(String administrativeAreaLevel2) {
        this.administrativeAreaLevel2 = administrativeAreaLevel2;
    }

    public String getAdministrativeAreaLevel3() {
        return administrativeAreaLevel3;
    }

    public void setAdministrativeAreaLevel3(String administrativeAreaLevel3) {
        this.administrativeAreaLevel3 = administrativeAreaLevel3;
    }

    public String getAdministrativeAreaLevel4() {
        return administrativeAreaLevel4;
    }

    public void setAdministrativeAreaLevel4(String administrativeAreaLevel4) {
        this.administrativeAreaLevel4 = administrativeAreaLevel4;
    }

    public String getAdministrativeAreaLevel5() {
        return administrativeAreaLevel5;
    }

    public void setAdministrativeAreaLevel5(String administrativeAreaLevel5) {
        this.administrativeAreaLevel5 = administrativeAreaLevel5;
    }
    
}
