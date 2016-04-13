package com.pub.lookup.service;

import com.pub.lookup.domain.PostCode;


public interface PubProviderService {
    
    public PostCode getPubInfo(String search) throws Exception;
    
}
