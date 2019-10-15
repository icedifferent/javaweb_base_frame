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

import com.pouruan.web.dao.AuthDao;
import com.pouruan.web.entity.Authentication;
@Repository
public class AuthDaoImpl implements AuthDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	//private Session session= sessionFactory.getCurrentSession();
	@Override
	public List<Authentication> getUserByKey(String authKey) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (Authentication. class);
		Criteria c = dc.getExecutableCriteria(session);
		dc.add(Restrictions. eq ("authenticationKey", authKey));
		//dc.add(Restrictions.gt("update_time", value))
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}
	@Override
	public List<Authentication> getUserByUserId(int userId) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (Authentication. class);
		Criteria c = dc.getExecutableCriteria(session);
		dc.add(Restrictions. eq ("userId", userId));
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}
	@Override
	public boolean editAuthentication(Authentication auth) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(auth);
		}catch(Exception e){
			logger.error("修改auth出错"+e);
			throw new Exception(e);
		}
		return true;
	}
	@Override
	public boolean addAuthentication(Authentication auth) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(auth);
		}catch(Exception e){
			logger.error("增加auth出错"+e);
			throw new Exception(e);
		}
		return true;
	}
}
