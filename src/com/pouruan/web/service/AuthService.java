package com.pouruan.web.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.entity.Authentication;
import com.pouruan.web.entity.User;

public interface AuthService {
	/**
	 * 通过authKey查找用户
	 * @param authKey
	 * @return User
	 */
	public User getUserByKey(String authKey)throws Exception;
	
	
	/**
	 * 增加授权记录
	 * @param userId
	 * @param authKey
	 * @param ip
	 * @return  Authentication
	 */
	public Authentication addAuthentication(int userId,String authKey,String ip) throws Exception;
}
