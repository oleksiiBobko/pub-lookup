package com.pub.lookup.dao;

import org.springframework.stereotype.Repository;

import com.pub.lookup.dao.base.HibernateDao;
import com.pub.lookup.dao.interfaces.PointDao;
import com.pub.lookup.domain.Point;

@Repository
public class PointDaoImpl extends HibernateDao<Point, String> implements PointDao {}
