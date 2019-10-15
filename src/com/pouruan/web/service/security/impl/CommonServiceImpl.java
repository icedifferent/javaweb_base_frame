package com.pouruan.web.service.security.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.security.CommonService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class CommonServiceImpl implements CommonService{
	@Autowired
	private UserService userService;

	@Override
	public boolean isCustomUser() {
		try{
			UserDetails userDetails = (UserDetails)( SecurityContextHolder
					.getContext()
				    .getAuthentication()
				    .getPrincipal());
			return true;
		}catch(Exception e){
			return false;
		}

		//System.out.println(userDetails.getUsername());
		
	}

	@Override
	public boolean isAnonymous() {
		return !isCustomUser();
	}

	@Override
	public User getCustomUser() {
		try{
			UserDetails userDetails = (UserDetails)( SecurityContextHolder
					.getContext()
				    .getAuthentication()
				    .getPrincipal());
			String phone=userDetails.getUsername();
			return userService.getByUserPhone(phone);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Admin getAdmin() {
		try{ 
			UserDetails userDetails = (UserDetails)( SecurityContextHolder
					.getContext()
				    .getAuthentication()
				    .getPrincipal());
			String phone=userDetails.getUsername();
			User user=userService.getByUserPhone(phone);
			if(user.getAdmin()!=null){
				return  user.getAdmin();
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public WebAuthenticationDetails getAuthenticationDetails() {
		return (WebAuthenticationDetails) SecurityContextHolder
				.getContext()
			    .getAuthentication().getDetails();
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder
				.getContext()
			    .getAuthentication().getAuthorities();
		return authorities;
		//for (GrantedAuthority grantedAuthority : authorities) {
		// System.out.println("Authority" + grantedAuthority.getAuthority());
		//}
	}

	@Override
	public UserDetails getUserDetails() {
		try{ 
			UserDetails userDetails = (UserDetails)( SecurityContextHolder
					.getContext()
				    .getAuthentication()
				    .getPrincipal());
			return userDetails;
		}catch(Exception e){
			return null;
		}
	}

}
