package com.pub.lookup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pub.lookup.dao.interfaces.DistanceDao;
import com.pub.lookup.dao.interfaces.PostCodeDao;
import com.pub.lookup.dao.interfaces.PubDao;
import com.pub.lookup.domain.Distance;
import com.pub.lookup.domain.PostCode;
import com.pub.lookup.domain.PubEntity;
import com.pub.lookup.service.PersistenceService;

@Service
@Transactional
public class PersistenceServiceImpl implements PersistenceService {

    @Autowired
    private PostCodeDao postCodeDao;
    
    @Autowired
    private PubDao pubDao;
    
    @Autowired
    private DistanceDao distanceDao;
    
    @Override
    public PostCode getPostCode(String postCode) {
        return postCodeDao.find(postCode);
    }

    @Override
    public PubEntity getPub(String pubId) {
        return pubDao.find(pubId);
    }

    @Override
    public void save(PostCode postCode) {
        postCodeDao.update(postCode);
        
    }

    @Override
    public void save(PubEntity pub) {
        pubDao.update(pub);
        
    }

    @Override
    public void save(Distance distance) {
        distanceDao.update(distance);
        
    }

}
