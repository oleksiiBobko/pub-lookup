package com.pub.lookup.service;

import com.pub.lookup.domain.PostCode;

public interface PostCodePersistenceService {
    
    public PostCode getPostCode(String code);
    
    public void saveOrUpdate(PostCode postCode);
    
}
