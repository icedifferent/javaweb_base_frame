package com.pouruan.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.PermissionDao;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.service.PermissionService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDao permissionDao;
	@Override
	public List<Permission> showAllPermission()  throws Exception{
		return permissionDao.showAllPermission(null);
	}
	@Override
	public Permission getPermissionById(int id)  throws Exception{
		Permission permission =new Permission();
		permission.setId(id);
		 List<Permission> list=permissionDao.showAllPermission(permission);
		 if(list.size()!=0){
			 return list.get(0);
		 }else{
			 return null;
		 }
	}

}
