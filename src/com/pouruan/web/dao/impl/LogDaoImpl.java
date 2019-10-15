package com.pouruan.web.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.LogDao;
import com.pouruan.web.entity.Log;
import com.pouruan.web.entity.User;

@Repository
public class LogDaoImpl implements LogDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean wirteLog(Log log) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(log);
			session.flush();
		}catch(Exception e){
			logger.equals("Ôö¼Ólog³ö´í"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Log> listLogByCondition(Log log,int pageNo,int pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (Log. class);
		Criteria c = dc.getExecutableCriteria(session);
		if(log.getUser()!=null){
			dc.add(Restrictions. eq ("user", log.getUser()));
		}
		c.addOrder(Order.desc("id"));
		
		try{
			return (List<Log>) c.setFirstResult((pageNo-1)*pageSize)  
			        .setMaxResults(pageSize)  
			        .list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getLogCountByCondition(Log log) throws Exception {
		String condition ="1=1 ";
		if(log.getUser()!=null){
			condition+=" And l.user ="+log.getUser();
		}
		String hql = "select count(*) FROM Log AS l WHERE "+condition;
		Session session= sessionFactory.getCurrentSession();
		Query<?> query =session.createQuery(hql);
		
		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

}
