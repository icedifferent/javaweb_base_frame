package com.pouruan.web.dao.extend.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.extend.ModuleDao;
import com.pouruan.web.entity.extend.Module;
@Repository
public class ModuleDaoImpl implements ModuleDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean addMoudle(Module module) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(module);
		}catch(Exception e){
			logger.error("增加module出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean delModule(Module module) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(module);
		}catch(Exception e){
			logger.error("删除module出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean editModule(Module module) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(module);
		}catch(Exception e){
			logger.error("修改module出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Module> listModuleByCondition(Module module, int pageNo,
			int pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(module!=null)
		if(module.getId()!=null){
			condition+=" And m.id = :id";
		}
		
		String hql = "FROM Module AS m WHERE "+condition+" order by m.id  desc";
		Query<?> query =session.createQuery(hql);
		if(module!=null)
		if(module.getId()!=null)
			query.setParameter("id", module.getId());
		
		try{
			return (List<Module>) query.setFirstResult((pageNo-1)*pageSize)  
			        .setMaxResults(pageSize)  
			        .list(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getCountByCondition(Module module) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(module!=null)
		if(module.getId()!=null){
			condition+=" And m.id = :id";
		}
		String hql = "Select count(*) FROM Module AS m WHERE "+condition;
		Query<?> query =session.createQuery(hql);
		if(module!=null)
		if(module.getId()!=null)
			query.setParameter("id", module.getId());
		
		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

}
