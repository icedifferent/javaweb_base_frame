package com.pouruan.web.service.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.User;


public interface CommonService {
	
	/**
	 * �Ƿ�Ϊ��¼�û�
	 * @return boolean
	 */
	public boolean isCustomUser();
	
	/**
	 * �Ƿ�Ϊ�����û�
	 * @return boolean
	 */
	public boolean isAnonymous();
	
	/**
	 * ��ȡ��¼�û�����Ϣ
	 * @return
	 */
	public User getCustomUser();
	
	
	
	/**
	 * ��ȡ����Ա��Ϣ
	 * @return
	 */
	public Admin getAdmin();
	
	
	/**
	 * ��ȡ��¼���WebAuthenticationDetails
	 * @return
	 */
	public WebAuthenticationDetails getAuthenticationDetails();
	
	
	/**
	 * ��ȡ�û�Ȩ��
	 * @return
	 */
	public List<GrantedAuthority> getAuthorities( );
	  
	 /**
	  * ��ȡUserDetails
	  * @return
	  */
	public UserDetails getUserDetails();
}
