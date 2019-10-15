package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Role;

public interface RoleDao {
	/**
	 * 通过id获取角色关联
	 * @param id
	 * @return List
	 */
	public  List<Role> getRoleById(String id) throws Exception ;
	
	/**
	 * 获取所有角色关联
	 * @return List
	 */
	public List<Role> getAllRole() throws Exception ;
	
	/**
	 * 增加新角色关联
	 * @param role
	 * @return boolean
	 */
	public boolean addRole(Role role ) throws Exception ;
	
	/**
	 * 修改角色关联
	 * @param role
	 * @return boolean
	 */
	public boolean editRole(Role role) throws Exception ;
	
	/**
	 * 删除角色关联
	 * @param role
	 * @return
	 */
	public boolean delRole(Role role) throws Exception ;
}
