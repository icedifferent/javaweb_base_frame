package com.pouruan.web.service;

import java.util.List;

import com.pouruan.web.entity.Permission;

public interface PermissionService {
	/**
	 * �г�����Ȩ��
	 * @return List<Permission>
	 */
	public List<Permission> showAllPermission() throws Exception;
	
	
	/**
	 * ����id��ȡȨ����Ϣ
	 * @param id
	 * @return
	 */
	public Permission getPermissionById(int id)  throws Exception;
}
