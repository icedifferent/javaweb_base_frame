package com.pouruan.web.service.security.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.pouruan.common.web.RequestUtils;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.session.SessionProvider;
import com.pouruan.web.component.ValidateSubmit;
import com.pouruan.web.service.ConfigService;
import com.pouruan.web.service.IpLimitService;


/**
 * 登录过滤器
 */
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
public class SecuriyLoginFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	private ConfigService configService;
	@Autowired
	private IpLimitService loginLogService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ValidateSubmit validateSubmit;
	private Logger logger = LogManager.getRootLogger();
	/**
	 * 重写验证方法
	 * 增加验证码验证
	 */
    @Override
    public Authentication attemptAuthentication (
            HttpServletRequest request,
            HttpServletResponse response)
                    throws AuthenticationException{
    	long actionTimes;
    	long maxActionTimes;
		try {
			actionTimes = loginLogService.getTimesByIp(RequestUtils.getIpAddr(request),0,true);
			maxActionTimes=configService.getConfig().getMaxErrorTimes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
    	
    	if(actionTimes>=maxActionTimes){//5次以上则需要验证码
    		String inputCode = request.getParameter("captcha");
    		WebErrors errors = WebErrors.create(request);
    		//验证验证码
    		validateSubmit.checkCaptcha(errors,inputCode,request,response);
    		if(errors.hasErrors()){
    			throw new AuthenticationServiceException("验证码异常");
    		}
    			
		}
        
        return super.attemptAuthentication(request, response);
    }

    
   
}