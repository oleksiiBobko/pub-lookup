package com.pub.lookup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="distance")
public class Distance {

    @Id
    @GeneratedValue
    @Column(name = "distanceId")
    private int distanceId;

    @ManyToOne
    @JoinColumn(name = "pubId")
    private PubEntity pubId;

    @ManyToOne
    @JoinColumn(name = "postCode")
    private PostCode postCode;

    @Column(name="distance")
    private String distance;

    @Column
    private double distanceMiles;

    @Column
    private double distanceKilometers;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getDistanceMiles() {
        return distanceMiles;
    }

    public void setDistanceMiles(double distanceMiles) {
        this.distanceMiles = distanceMiles;
    }

    public double getDistanceKilometers() {
        return distanceKilometers;
    }

    public void setDistanceKilometers(double distanceKilometers) {
        this.distanceKilometers = distanceKilometers;
    }

    public int getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(int distanceId) {
        this.distanceId = distanceId;
    }

    public PubEntity getPub() {
        return pubId;
    }

    public void setPub(PubEntity pub) {
        this.pubId = pub;
    }

    public PostCode getPostCode() {
        return postCode;
    }

    public void setPostCode(PostCode postCode) {
        this.postCode = postCode;
    }

}
