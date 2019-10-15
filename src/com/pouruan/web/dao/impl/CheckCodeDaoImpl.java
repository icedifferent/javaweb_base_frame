package com.pouruan.web.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.CheckCodeDao;
import com.pouruan.web.entity.CheckCode;

@Repository
public class CheckCodeDaoImpl implements CheckCodeDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean addCheckCode(CheckCode checkCode) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(checkCode);
		}catch(Exception e){
			logger.error("增加checkCode出错"+e);
			throw new Exception(e);
		}
		return true;
	}


	@Override
	public boolean editCheckCode(CheckCode checkCode) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(checkCode);
		}catch(Exception e){
			logger.error("修改checkCode出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<CheckCode> getCheckCodeByObject(CheckCode checkCode) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (CheckCode. class);
		Criteria c = dc.getExecutableCriteria(session);
		dc.add(Restrictions. eq ("checkVar", checkCode.getCheckVar()));
		dc.add(Restrictions. eq ("status", checkCode.getStatus()));
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}
}
