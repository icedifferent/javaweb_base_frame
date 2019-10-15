package com.pouruan.web.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.ConfigDao;
import com.pouruan.web.entity.Config;

@Repository
public class ConfigDaoImpl implements ConfigDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public Config getConfig() throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			Config config=session.load(Config.class, 1);
			if(config==null){
				logger.error("加载网站配置文件出错");
			}
			return config;
		}catch(Exception e){
			throw new Exception(e);
		}
	}
	
	
	
	@Override
	public boolean  editConfig(Config config) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(config);
		}catch(Exception e){
			logger.error("修改config出错"+e);
			throw new Exception(e);
		}
		return true;
	}
}
