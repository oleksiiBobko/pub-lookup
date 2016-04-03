package com.pub.lookup.dao;

import org.springframework.stereotype.Repository;

import com.pub.lookup.dao.base.HibernateDao;
import com.pub.lookup.dao.interfaces.PostCodeDao;
import com.pub.lookup.domain.PostCode;

@Repository
public class PostCodeDaoImpl extends HibernateDao<PostCode, String> implements PostCodeDao {}
