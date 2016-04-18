package com.pub.lookup.service;

import com.pub.lookup.domain.GeoSearch;

public interface GeoApiService {
    public GeoSearch validateSearch(String search) throws Exception;
    
    public String getSearchStringByPriority(String search);
}
