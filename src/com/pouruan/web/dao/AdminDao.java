package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Admin;

public interface AdminDao {
	/**
	 * 通过用户id查询此用户对应的管理员
	 * @param userId
	 * @return List
	 */
	public List<Admin> getAdmin(Admin admin)throws Exception;
	
	/**
	 * 增加管理员
	 * @param admin
	 * @return boolean
	 */
	public boolean addAdmin(Admin admin)throws Exception;
	
	
	/**
	 * 修改管理员
	 * @param admin
	 * @return boolean
	 */
	public boolean editAdmin(Admin admin)throws Exception;
	
	
	/**
	 * 增加管理员
	 * @param admin
	 * @return
	 */
	public boolean delAdmin(Admin admin)throws Exception;
}
