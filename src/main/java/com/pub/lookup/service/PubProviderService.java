package com.pub.lookup.service;

import com.pub.lookup.domain.GeoSearch;
import com.pub.lookup.domain.Point;
import com.pub.lookup.domain.PubEntity;


public interface PubProviderService {
    
    public Point getPubInfo(String geoResult) throws Exception;

    public PubEntity getPubById(String pubId);
    
}
