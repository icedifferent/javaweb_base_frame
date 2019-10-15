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

import com.pouruan.web.dao.IpLimitDao;
import com.pouruan.web.entity.IpLimit;
@Repository
public class IpLimitDaoImpl implements IpLimitDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	//private Session session= sessionFactory.getCurrentSession();
	@Override
	public List<IpLimit> getIpLimitByIp(String ip,byte type) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (IpLimit. class);
		Criteria c = dc.getExecutableCriteria(session);
		dc.add(Restrictions. eq ("ip", ip));
		dc.add(Restrictions. eq ("type", type));
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}
	@Override
	public boolean addIpLimit(IpLimit ipLimit) throws Exception {Session session= sessionFactory.getCurrentSession();
		try{
			session.save(ipLimit);
		}catch(Exception e){
			logger.error("增加ipLimit出错"+e);
			throw new Exception(e);
		}
		return true;
	}
	@Override
	public boolean editIpLimit(IpLimit ipLimit) throws Exception {Session session= sessionFactory.getCurrentSession();
		try{
			session.update(ipLimit);
		}catch(Exception e){
			logger.error("修改ipLimit出错"+e);
			throw new Exception(e);
		}
		return true;
	}
}
