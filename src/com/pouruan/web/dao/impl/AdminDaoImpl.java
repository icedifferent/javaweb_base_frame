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

import com.pouruan.web.dao.AdminDao;
import com.pouruan.web.entity.Admin;
@Repository
public class AdminDaoImpl implements AdminDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public List<Admin> getAdmin(Admin admin) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (Admin. class);
		Criteria c = dc.getExecutableCriteria(session);
		if(admin!=null){
			if(admin.getUser()!=null){
				dc.add(Restrictions. eq ("user", admin.getUser()));
			}
		}
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public boolean addAdmin(Admin admin) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(admin);
		}catch(Exception e){
			logger.error("修改admin出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean editAdmin(Admin admin) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(admin);
		}catch(Exception e){
			logger.error("修改admin出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	
	@Override
	public boolean delAdmin(Admin admin) throws Exception{
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(admin);;
		}catch(Exception e){
			logger.error("删除admin出错"+e);
			throw new Exception(e);
		}
		return true;
	}
}
