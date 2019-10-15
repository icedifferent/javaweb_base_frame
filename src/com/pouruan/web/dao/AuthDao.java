package com.pouruan.web.dao;

import java.util.List;




import com.pouruan.web.entity.Authentication;


public interface AuthDao {
	/**
	 * ͨ��authentication��ȡ�û�
	 * @param authKey
	 * @return User
	 */
	public List<Authentication> getUserByKey(String authKey) throws Exception;
	
	/**
	 * ͨ��userId��ȡ��֤
	 * @param userId
	 * @return
	 */
	public List<Authentication> getUserByUserId (int userId) throws Exception;
	
	
	/**
	 * �޸���֤��¼
	 * @param auth
	 * @return
	 */
	public boolean editAuthentication(Authentication  auth) throws Exception;
	
	/**
	 * ������֤��¼
	 * @param auth
	 * @return
	 */
	public boolean addAuthentication(Authentication  auth) throws Exception;
}
