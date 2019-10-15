package com.pouruan.web.service;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.Role;


public interface RoleService {
	/**
	 * 增加角色关联
	 * @param userId
	 * @param permissionId
	 * @return boolean
	 */
	public boolean  addRole(Admin admin, Permission permission)  throws Exception;
	
	/**
	 *  增加角色关联
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role) throws Exception;
	
	/**
	 * 查找角色关联
	 * @param userId
	 * @return Role
	 */
	public Role findRoleByUser(int userId) throws Exception;
	
	/**
	 * 删除角色关联
	 * @param role
	 * @return
	 */
	public boolean delRole(Role role) throws Exception;
	
	/**
	 *  删除角色关联
	 * @param admin
	 * @param permission
	 * @return boolean
	 */
	public boolean delRole(Admin admin, Permission permission) throws Exception;
}
