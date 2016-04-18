package com.pub.lookup.dao;

import org.springframework.stereotype.Repository;

import com.pub.lookup.dao.base.HibernateDao;
import com.pub.lookup.dao.interfaces.GeoSearchDao;
import com.pub.lookup.domain.GeoSearch;

@Repository
public class GeoSearchDaoImpl extends HibernateDao<GeoSearch, String> implements GeoSearchDao {}
