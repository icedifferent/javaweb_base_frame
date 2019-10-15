package com.pouruan.web.service.security.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Role;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.UserService;


@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserService userService;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public UserDetails loadUserByUsername(String phone)
			throws UsernameNotFoundException {
		User user;
		try {
			user = userService.getByUserPhone(phone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			user =null;
		}
		if(user==null){
			throw new UsernameNotFoundException("Username not found");
		}
			return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getUserPwd(), 
				 user.getStatus()==(byte)1, true, true, true, getGrantedAuthorities(user));
	}

	
	
	/**
	 * 获取用户角色
	 * @param user
	 * @return List<GrantedAuthority>
	 */
	private List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Admin admin=user.getAdmin();
		if(admin!=null){//是管理员
			Set<Role> set = admin.getRoles();
			Iterator<Role> it = set.iterator();
			while (it.hasNext()) {  //拥有的权限
				 Role role = it.next();
				 authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getPermission().getName()));
				 logger.info(role.getPermission().getName());
			}
		}
		//每个登录后的用户都有普通用户权限
		if(user.getStatus()==0){
			authorities.add(new SimpleGrantedAuthority("ROLE_UNACTIVE"));	
		}else{
			authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMUSER"));	
		}
		
		return authorities;
	}
}
