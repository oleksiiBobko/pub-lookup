package com.pub.lookup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pub.lookup.dao.interfaces.PostCodeDao;
import com.pub.lookup.domain.PostCode;
import com.pub.lookup.service.PostCodePersistenceService;

@Service
@Transactional
public class PostCodePersistenceServiceImpl implements PostCodePersistenceService {

    @Autowired
    private PostCodeDao postCodeDao;
    
    @Override
    public PostCode getPostCode(String code) {
        return postCodeDao.find(code);
    }

    @Override
    public void saveOrUpdate(PostCode postCode) {
        postCodeDao.update(postCode);
        
    }

}
