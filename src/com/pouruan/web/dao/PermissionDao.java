package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Permission;

public interface PermissionDao {
	/**
	 * �г�Ȩ��
	 * @return
	 */
	public List<Permission> showAllPermission(Permission permission)throws Exception;
	
	
}
