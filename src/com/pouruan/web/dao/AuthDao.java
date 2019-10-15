package com.pouruan.web.dao;

import java.util.List;




import com.pouruan.web.entity.Authentication;


public interface AuthDao {
	/**
	 * 通过authentication获取用户
	 * @param authKey
	 * @return User
	 */
	public List<Authentication> getUserByKey(String authKey) throws Exception;
	
	/**
	 * 通过userId获取认证
	 * @param userId
	 * @return
	 */
	public List<Authentication> getUserByUserId (int userId) throws Exception;
	
	
	/**
	 * 修改认证记录
	 * @param auth
	 * @return
	 */
	public boolean editAuthentication(Authentication  auth) throws Exception;
	
	/**
	 * 增加认证记录
	 * @param auth
	 * @return
	 */
	public boolean addAuthentication(Authentication  auth) throws Exception;
}
