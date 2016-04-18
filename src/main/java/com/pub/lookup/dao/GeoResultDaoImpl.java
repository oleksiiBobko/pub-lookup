package com.pub.lookup.dao;

import org.springframework.stereotype.Repository;

import com.pub.lookup.dao.base.HibernateDao;
import com.pub.lookup.dao.interfaces.GeoResultDao;
import com.pub.lookup.domain.GeoResult;

@Repository
public class GeoResultDaoImpl extends HibernateDao<GeoResult, Integer> implements GeoResultDao {}
