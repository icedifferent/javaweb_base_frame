package com.pouruan.web.service;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.Role;


public interface RoleService {
	/**
	 * ���ӽ�ɫ����
	 * @param userId
	 * @param permissionId
	 * @return boolean
	 */
	public boolean  addRole(Admin admin, Permission permission)  throws Exception;
	
	/**
	 *  ���ӽ�ɫ����
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role) throws Exception;
	
	/**
	 * ���ҽ�ɫ����
	 * @param userId
	 * @return Role
	 */
	public Role findRoleByUser(int userId) throws Exception;
	
	/**
	 * ɾ����ɫ����
	 * @param role
	 * @return
	 */
	public boolean delRole(Role role) throws Exception;
	
	/**
	 *  ɾ����ɫ����
	 * @param admin
	 * @param permission
	 * @return boolean
	 */
	public boolean delRole(Admin admin, Permission permission) throws Exception;
}
