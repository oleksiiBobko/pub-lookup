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
@Table(name="point")
public class Point {

    @Id
    @NotNull
    @NotEmpty
    @Column(name = "point_id")
    private String pointId;
    
    @OneToMany(targetEntity = Distance.class, mappedBy = "point", 
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Distance> distances;

    public Point() {
        super();
        this.distances = new ArrayList<Distance>();
    }

    public Point(String pointId, List<Distance> distances) {
        super();
        this.pointId = pointId;
        this.distances = distances;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }
    
}
