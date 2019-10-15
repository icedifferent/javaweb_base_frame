package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Permission;

public interface PermissionDao {
	/**
	 * 列出权限
	 * @return
	 */
	public List<Permission> showAllPermission(Permission permission)throws Exception;
	
	
}
