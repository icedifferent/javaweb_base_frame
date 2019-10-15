package com.pouruan.web.service;

import java.util.List;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.User;

public interface AdminService {
	/**
	 * 增加管理员
	 * @param user
	 * @param permission
	 * @return boolean
	 */
	public boolean addRole(User user,Permission permission)throws Exception ;
	
	/**
	 * 删除管理员
	 * @param user
	 * @param permissionId
	 * @return boolean
	 */
	public boolean delAdmin(User user)throws Exception ;
	/**
	 * 删除管理员
	 * @param admin
	 * @return boolean
	 */
	public boolean delAdmin(Admin admin)throws Exception ;
	
	/**
	 * 列出所有管理员
	 * @return
	 */
	public List<Admin> getAllAdmin()throws Exception ;
}
