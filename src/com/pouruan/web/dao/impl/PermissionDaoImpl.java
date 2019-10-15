package com.pouruan.web.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.PermissionDao;
import com.pouruan.web.entity.Permission;
@Repository
public class PermissionDaoImpl implements PermissionDao{
	@Autowired
	private  SessionFactory sessionFactory;
	/**
	 * 列出所有权限
	 * @return List<Permission>
	 */
	@Override
	public List<Permission> showAllPermission(Permission permission) throws Exception  {
			Session session= sessionFactory.getCurrentSession();
			DetachedCriteria dc = DetachedCriteria. forClass (Permission. class);
			Criteria c = dc.getExecutableCriteria(session);
			if(permission!=null){
				if(permission.getId()!=null){
					dc.add(Restrictions. eq ("id", permission.getId()));
				}
				if(permission.getName()!=null){
					dc.add(Restrictions. eq ("name", permission.getName()));
				}
			}
			try{
				return c.list();
			}catch(Exception e){
				throw new Exception(e);
			}
	}
}
