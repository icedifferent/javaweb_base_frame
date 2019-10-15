package com.pouruan.web.service;

import java.util.List;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.User;

public interface AdminService {
	/**
	 * ���ӹ���Ա
	 * @param user
	 * @param permission
	 * @return boolean
	 */
	public boolean addRole(User user,Permission permission)throws Exception ;
	
	/**
	 * ɾ������Ա
	 * @param user
	 * @param permissionId
	 * @return boolean
	 */
	public boolean delAdmin(User user)throws Exception ;
	/**
	 * ɾ������Ա
	 * @param admin
	 * @return boolean
	 */
	public boolean delAdmin(Admin admin)throws Exception ;
	
	/**
	 * �г����й���Ա
	 * @return
	 */
	public List<Admin> getAllAdmin()throws Exception ;
}
