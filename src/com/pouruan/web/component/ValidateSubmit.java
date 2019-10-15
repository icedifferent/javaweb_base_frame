package com.pouruan.web.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.session.SessionProvider;
import com.pouruan.web.entity.Config;

/**
 * 验证码验证，数据合法性验证
 * @author Administrator
 *
 */
@Component
public class ValidateSubmit {
	@Autowired
	private  ImageCaptchaService imageCaptchaService;
	@Autowired
	private  SessionProvider session;
	private Logger logger = LogManager.getRootLogger();
	/**
	 * 验证登录提交的信息的合法性
	 * @param username
	 * @param password
	 * @param captcha
	 * @param request
	 * @param response
	 * @return WebErrors
	 */
	public   WebErrors validateSubmit(String phone, String password,
			String captcha,  HttpServletRequest request,
			HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNotPhone(phone, "phone", 11, 11)) {
			return errors;
		}
		if (errors.ifOutOfLength(password, "password", 6, 32)) {
			return errors;
		}
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		if (!StringUtils.isBlank(captcha)) {
			errors=checkCaptcha(errors, captcha, request, response);
		}
		return errors;
	}

	/**
	 * 验证登录提交的信息的合法性
	 * @param username
	 * @param password
	 * @param captcha
	 * @param request
	 * @param response
	 * @return WebErrors
	 */
	public   WebErrors validateEmailSubmit(String email, String password,
			String captcha,  HttpServletRequest request,
			HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNotEmail(email, "email",4, 100)){
			return errors;
		}
		if (errors.ifOutOfLength(password, "password", 6, 32)) {
			return errors;
		}
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		if (!StringUtils.isBlank(captcha)) {
			errors=checkCaptcha(errors, captcha, request, response);
		}
		return errors;
	}
	
	/**
	 * 验证邮箱和用户名是否合法
	 * @param email
	 * @param userName
	 * @param request
	 * @param response
	 * @return WebErrors
	 */
	public WebErrors validateEmailAndUserName(String email,String userName,HttpServletRequest request,
			HttpServletResponse response){
			WebErrors errors = WebErrors.create(request);
			if(errors.ifNotEmail(email, "email",4, 100)){
				return errors;
			}
			if(errors.ifNotRealname(userName, "realName", 1, 25)){
				return errors;
			}
			return errors;
	}
	
	
	/**
	 * 验证要发送的站内消息
	 * @param content
	 * @param toUserId
	 * @param request
	 * @param response
	 * @return
	 */
	public WebErrors validateMessage(String content,int toUserId,HttpServletRequest request,
			HttpServletResponse response){
			WebErrors errors = WebErrors.create(request);
			if(errors.ifOutOfLength(content, "content",1, 256)){
				return errors;
			}
			if(errors.ifNotNumber(Integer.toString(toUserId), "toUserId", 1, 12)){
				return errors;
			}
			return errors;
	}
	/**
	 * 验证提交的系统配置
	 * @param config
	 * @param request
	 * @param response
	 * @return
	 */
	public WebErrors validateConfig(Config config,HttpServletRequest request,
			HttpServletResponse response){
			WebErrors errors = WebErrors.create(request);
			if(errors.ifOutOfLength(config.getDomain(), "Domain",1, 30)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getEmailActContent(), "EmailActContent",1,255)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getEmailHost(), "EmailHost",1,100)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getEmailPwd(), "EmailPwd",1,100)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getEmailPwdContent(), "PwdContent",1,255)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getEmailTitle(), "EmailTitle",1,100)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getEmailUserName(), "EmailUserName",1,100)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getWebDescription(), "WebDescription",1,100)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getWebKeyWord(), "WebKeyWord",1,100)){
				return errors;
			}
			if(errors.ifOutOfLength(config.getWebTitle(), "WebTitle",1,30)){
				return errors;
			}
			if(errors.ifNotNumber(Integer.toString(config.getMaxErrorTimes()), "MaxErrorTimes",1,2)){
				return errors;
			}
			if(errors.ifNotNumber(Integer.toString(config.getMaxSendEmailTimes()), "MaxSendEmailTimes",1,2)){
				return errors;
			}
			if(errors.ifNotNumber(Integer.toString(config.getMaxSendMesTimes()), "MaxSendMesTimes",1,2)){
				return errors;
			}
			if(config.getId()!=1){
				errors.addError("ID越界错误");
			}
			return errors;
	}
	/**
	 * 验证验证码的正确性
	 * @param errors
	 * @param captcha
	 * @param request
	 * @param response
	 * @return WebErrors
	 */
	public   WebErrors checkCaptcha(WebErrors errors,String captcha, HttpServletRequest request,
			HttpServletResponse response){
		if(errors.ifNotLetter(captcha, "captcha", 4, 4)){
			return errors;
		}
		try {
			if (!imageCaptchaService.validateResponseForID(session
					.getSessionId(request, response), captcha)) {
				errors.addErrorCode("error.invalidCaptcha");
				logger.debug("验证码错误");
				return errors;
			}
		} catch (CaptchaServiceException e) {
			errors.addErrorCode("error.exceptionCaptcha");
			logger.error("验证码异常"+e.getMessage());
			return errors;
		}
		logger.debug("验证码正确");
		return errors;
	}
	
}
