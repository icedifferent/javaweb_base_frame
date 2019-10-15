package com.pouruan.web.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pouruan.common.exception.BadCredentialsException;
import com.pouruan.common.exception.UserPhoneNotFoundException;
import com.pouruan.web.entity.User;
public interface UserService {
	
	
	
	
	/**
	 * 通过用户名和密码进行登录校验
	 * @param username
	 * @param password
	 * @return	User
	 * @throws UserPhoneNotFoundException
	 * @throws BadCredentialsException
	 */
	public User login(String username, String password) 
			throws UserPhoneNotFoundException,BadCredentialsException,Exception;
	
	
	
	
	 
	/**
	 * 登录成功，写入auth表和cookie
	 * @param request
	 * @param response
	 * @param user
	 * @param sessionId
	 * @return boolean
	 */
	public boolean loginSuccess(HttpServletRequest request, HttpServletResponse response,User user,String sessionId)throws Exception;

	/**
	 * 根据手机号码查找用户
	 * @param username
	 * @return User
	 */
	public User getByUserPhone (String username) throws Exception;
	
	/**
	 * 通过id查找用户
	 * @param userId
	 * @return User
	 */
	public User getUserByUserId(int userId)throws Exception;

	/**
	 * 通过邮箱查找用户
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email)throws Exception;
	
	
	/**
	 * 根据某种条件列出用户
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return List<User>
	 */
	public List<User> getUserByCondition(User user,int pageNo,int  pageSize)throws Exception;

	/**
	 * 根据某种条件计算用户的数量
	 * @param user
	 * @return int
	 */
	public int  getUserCountByCondition(User user)throws Exception;
	/**
	 * 增加新用户
	 * @param user
	 * @return boolean
	 */
	public boolean addUser(String phone,String password,HttpServletRequest request)  throws Exception ;

	
	
	/**
	 * 修改用户
	 * @param user
	 * @return boolean
	 */
	public boolean editUser(User user)throws Exception;
	
	/**
	 * 修改用户个人信息
	 * @param User user
	 * @param userName
	 * @param email
	 * @return
	 */
	public boolean editUserInfo(User user,String userName,String email)throws Exception;
	
	
	
	
	/**
	 * 把此用户踢下线
	 * @param userId
	 * @return
	 */
	public void kickoutUser( String   phone)throws Exception ;
	/**
	 * 退出登录
	 */
	public void loginOut()throws Exception;
}
