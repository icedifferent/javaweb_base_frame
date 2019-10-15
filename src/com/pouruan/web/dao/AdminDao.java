package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Admin;

public interface AdminDao {
	/**
	 * ͨ���û�id��ѯ���û���Ӧ�Ĺ���Ա
	 * @param userId
	 * @return List
	 */
	public List<Admin> getAdmin(Admin admin)throws Exception;
	
	/**
	 * ���ӹ���Ա
	 * @param admin
	 * @return boolean
	 */
	public boolean addAdmin(Admin admin)throws Exception;
	
	
	/**
	 * �޸Ĺ���Ա
	 * @param admin
	 * @return boolean
	 */
	public boolean editAdmin(Admin admin)throws Exception;
	
	
	/**
	 * ���ӹ���Ա
	 * @param admin
	 * @return
	 */
	public boolean delAdmin(Admin admin)throws Exception;
}
