package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.User;
public interface UserDao{


	/**
	 * 增加用户
	 * @param user
	 * @return boolean
	 */
	public boolean addUser(User user) throws Exception ;
	
	
	/**
	 * 修改用户
	 * @param user
	 * @return boolean
	 */
	public boolean editUser(User user)throws Exception ;
	
	/**
	 * 根据条件获取用户
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return List<User>
	 */
	public List<User> getUserByCondition(User user,int pageNo,int pageSize)throws Exception ;
	
	
	/**
	 * 按条件获取用户的数量
	 * @param user
	 * @return int
	 */
	public  int getUserCountByCondition (User user) throws Exception ;
}
