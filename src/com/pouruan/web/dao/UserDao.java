package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.User;
public interface UserDao{


	/**
	 * �����û�
	 * @param user
	 * @return boolean
	 */
	public boolean addUser(User user) throws Exception ;
	
	
	/**
	 * �޸��û�
	 * @param user
	 * @return boolean
	 */
	public boolean editUser(User user)throws Exception ;
	
	/**
	 * ����������ȡ�û�
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return List<User>
	 */
	public List<User> getUserByCondition(User user,int pageNo,int pageSize)throws Exception ;
	
	
	/**
	 * ��������ȡ�û�������
	 * @param user
	 * @return int
	 */
	public  int getUserCountByCondition (User user) throws Exception ;
}
