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
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.RoleDao;
import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Role;
import com.pouruan.web.entity.User;
@Repository
public class RoleDaoImpl implements RoleDao{
	@Autowired
	private  SessionFactory sessionFactory;

	private Logger logger = LogManager.getRootLogger();
	@Override
	public List<Role> getRoleById(String id) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (Role. class);
		Criteria c = dc.getExecutableCriteria(session);
		dc.add(Restrictions. eq ("roleId", id));
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public List<Role> getAllRole() throws Exception {
		Session session= sessionFactory.getCurrentSession();
		DetachedCriteria dc = DetachedCriteria. forClass (Role. class);
		Criteria c = dc.getExecutableCriteria(session);
		try{
			return c.list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public boolean addRole(Role role)  throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(role);
		}catch(Exception e){
			logger.error("修改role出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean editRole(Role role) throws Exception  {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(role);
		}catch(Exception e){
			logger.error("修改role出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean delRole(Role role)  throws Exception {
			Session session= sessionFactory.getCurrentSession();
			try{
				session.delete(role);
			}catch(Exception e){
				logger.error("删除role出错"+e);
				throw new Exception(e);
			}
			return true;
	}
	


}
