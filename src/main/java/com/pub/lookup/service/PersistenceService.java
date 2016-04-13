package com.pub.lookup.service;

import com.pub.lookup.domain.Distance;
import com.pub.lookup.domain.PostCode;
import com.pub.lookup.domain.PubEntity;

public interface PersistenceService {
    
    public PostCode getPostCode(String code);
    
    public PubEntity getPub(String pubId);
    
    public void save(PostCode postCode);
    
    public void save(PubEntity pub);
    
    public void save(Distance distance);
    
}
