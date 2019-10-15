package com.pouruan.web.service;

import java.util.List;

import com.pouruan.web.entity.Permission;

public interface PermissionService {
	/**
	 * 列出所有权限
	 * @return List<Permission>
	 */
	public List<Permission> showAllPermission() throws Exception;
	
	
	/**
	 * 根据id获取权限信息
	 * @param id
	 * @return
	 */
	public Permission getPermissionById(int id)  throws Exception;
}
