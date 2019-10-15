package com.pouruan.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.pouruan.web.entity.parent.AuthenticationParent;

public class Authentication extends AuthenticationParent  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Authentication(){};
	public Authentication(int userId,String authenticationKey,String loginIp,Timestamp loginTime,Timestamp updateTime,User user){
		this.setUserId(userId);
		this.setAuthenticationKey(authenticationKey);
		this.setLoginIp(loginIp);
		this.setLoginTime(loginTime);
		this.setUpdateTime(updateTime);
		if(user!=null)
			this.setUser(user);
	}
}
