package com.pouruan.web.service.security.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.service.IpLimitService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.entity.User;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Autowired
	private IpLimitService loginLogService;
	@Autowired
	private UserService userService;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        //获得用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //获得登录后的信息 // securityContextImpl.getAuthentication().getDetails();  
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();  
		//写入认证表以及cookie
       // User user = userService.getByUserPhone(userDetails.getUsername());
		//userService.loginSuccess(request, response, user,details.getSessionId());
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
       
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
    
    protected String determineTargetUrl(Authentication authentication) {
    	String url="";
    	
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        
		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

	/*	if (isAdmin(roles)) {
			url = "/User/index.do";
		} else if (isUser(roles)) {
			url = "/User/index.do";
		} else {
			url = "/User/index.do";
		}*/
		url = "/User/index.do";
		return url;
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_CUMSTOMER")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_BANZHU")) {
			return true;
		}
		return false;
	}


} 
