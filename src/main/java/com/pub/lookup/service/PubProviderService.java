package com.pub.lookup.service;

import com.pub.lookup.domain.GeoSearch;
import com.pub.lookup.domain.Point;


public interface PubProviderService {
    
    public Point getPubInfo(String geoResult) throws Exception;
    
}
