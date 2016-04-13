package com.pub.lookup.dao;

import org.springframework.stereotype.Repository;

import com.pub.lookup.dao.base.HibernateDao;
import com.pub.lookup.dao.interfaces.DistanceDao;
import com.pub.lookup.domain.Distance;

@Repository
public class DistanceDaoImpl extends HibernateDao<Distance, Integer> implements DistanceDao {}
