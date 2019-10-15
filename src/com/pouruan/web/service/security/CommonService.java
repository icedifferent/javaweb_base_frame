package com.pouruan.web.service.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.User;


public interface CommonService {
	
	/**
	 * 是否为登录用户
	 * @return boolean
	 */
	public boolean isCustomUser();
	
	/**
	 * 是否为匿名用户
	 * @return boolean
	 */
	public boolean isAnonymous();
	
	/**
	 * 获取登录用户的信息
	 * @return
	 */
	public User getCustomUser();
	
	
	
	/**
	 * 获取管理员信息
	 * @return
	 */
	public Admin getAdmin();
	
	
	/**
	 * 获取登录后的WebAuthenticationDetails
	 * @return
	 */
	public WebAuthenticationDetails getAuthenticationDetails();
	
	
	/**
	 * 获取用户权限
	 * @return
	 */
	public List<GrantedAuthority> getAuthorities( );
	  
	 /**
	  * 获取UserDetails
	  * @return
	  */
	public UserDetails getUserDetails();
}
