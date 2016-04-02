package com.pub.lookup.domain;

public class PubEntity {

    private String pubName;
    
    private String area;
    
    public PubEntity() {
        super();
    }
    
    public PubEntity(String pubName, String area) {
        this.pubName = pubName;
        this.area = area;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
