package com.pub.lookup.dao;

import org.springframework.stereotype.Repository;

import com.pub.lookup.dao.base.HibernateDao;
import com.pub.lookup.dao.interfaces.PubDao;
import com.pub.lookup.domain.PubEntity;

@Repository
public class PubDaoImpl  extends HibernateDao<PubEntity, String> implements PubDao {}
