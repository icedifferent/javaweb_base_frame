package com.pouruan.web.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.entity.Authentication;
import com.pouruan.web.entity.User;

public interface AuthService {
	/**
	 * ͨ��authKey�����û�
	 * @param authKey
	 * @return User
	 */
	public User getUserByKey(String authKey)throws Exception;
	
	
	/**
	 * ������Ȩ��¼
	 * @param userId
	 * @param authKey
	 * @param ip
	 * @return  Authentication
	 */
	public Authentication addAuthentication(int userId,String authKey,String ip) throws Exception;
}
