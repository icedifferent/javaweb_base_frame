package com.pouruan.web.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pouruan.common.exception.BadCredentialsException;
import com.pouruan.common.exception.UserPhoneNotFoundException;
import com.pouruan.web.entity.User;
public interface UserService {
	
	
	
	
	/**
	 * ͨ���û�����������е�¼У��
	 * @param username
	 * @param password
	 * @return	User
	 * @throws UserPhoneNotFoundException
	 * @throws BadCredentialsException
	 */
	public User login(String username, String password) 
			throws UserPhoneNotFoundException,BadCredentialsException,Exception;
	
	
	
	
	 
	/**
	 * ��¼�ɹ���д��auth���cookie
	 * @param request
	 * @param response
	 * @param user
	 * @param sessionId
	 * @return boolean
	 */
	public boolean loginSuccess(HttpServletRequest request, HttpServletResponse response,User user,String sessionId)throws Exception;

	/**
	 * �����ֻ���������û�
	 * @param username
	 * @return User
	 */
	public User getByUserPhone (String username) throws Exception;
	
	/**
	 * ͨ��id�����û�
	 * @param userId
	 * @return User
	 */
	public User getUserByUserId(int userId)throws Exception;

	/**
	 * ͨ����������û�
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email)throws Exception;
	
	
	/**
	 * ����ĳ�������г��û�
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @return List<User>
	 */
	public List<User> getUserByCondition(User user,int pageNo,int  pageSize)throws Exception;

	/**
	 * ����ĳ�����������û�������
	 * @param user
	 * @return int
	 */
	public int  getUserCountByCondition(User user)throws Exception;
	/**
	 * �������û�
	 * @param user
	 * @return boolean
	 */
	public boolean addUser(String phone,String password,HttpServletRequest request)  throws Exception ;

	
	
	/**
	 * �޸��û�
	 * @param user
	 * @return boolean
	 */
	public boolean editUser(User user)throws Exception;
	
	/**
	 * �޸��û�������Ϣ
	 * @param User user
	 * @param userName
	 * @param email
	 * @return
	 */
	public boolean editUserInfo(User user,String userName,String email)throws Exception;
	
	
	
	
	/**
	 * �Ѵ��û�������
	 * @param userId
	 * @return
	 */
	public void kickoutUser( String   phone)throws Exception ;
	/**
	 * �˳���¼
	 */
	public void loginOut()throws Exception;
}
