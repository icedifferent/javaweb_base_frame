package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Role;

public interface RoleDao {
	/**
	 * ͨ��id��ȡ��ɫ����
	 * @param id
	 * @return List
	 */
	public  List<Role> getRoleById(String id) throws Exception ;
	
	/**
	 * ��ȡ���н�ɫ����
	 * @return List
	 */
	public List<Role> getAllRole() throws Exception ;
	
	/**
	 * �����½�ɫ����
	 * @param role
	 * @return boolean
	 */
	public boolean addRole(Role role ) throws Exception ;
	
	/**
	 * �޸Ľ�ɫ����
	 * @param role
	 * @return boolean
	 */
	public boolean editRole(Role role) throws Exception ;
	
	/**
	 * ɾ����ɫ����
	 * @param role
	 * @return
	 */
	public boolean delRole(Role role) throws Exception ;
}
