package com.pouruan.common.web.tips;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * WEB错误信息处理
 * 
 * 可以通过MessageSource实现国际化。
 * 
 */
public abstract class WebErrorsAbstract {

	/**
	 * email正则表达式
	 */
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$");
	
	/**
	 * 验证手机号码正则表达式
	 */
	public static final Pattern PHONE_PATTERN =Pattern
			.compile("^[1][3,4,5,8][0-9]{9}$");
	
	/**
	 * 验证是否为数字正则表达式
	 */
	public static final Pattern NUMBER_PATTERN =Pattern
			.compile("[0-9]*");
	
	/**
	 * 验证是否为英文正则表达式
	 */
	public static final Pattern LETTER_PATTERN =Pattern
			.compile("^[a-zA-Z]*");
	
	
	/**
	 * username正则表达式
	 */
	public static final Pattern USERNAME_PATTERN = Pattern
			.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$");
	
	/**
	 * 真实姓名正则表达式
	 */
	public static final Pattern REALNAME_PATTERN = Pattern
			.compile("(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,15}))");
	/**
	 * 无参构造函数
	 */
	public WebErrorsAbstract() {
	}
	
	/**
	 * 通过HttpServletRequest创建WebErrors
	 * 
	 * @param request
	 *            从request中获得MessageSource和Locale，如果存在的话。
	 */
	public WebErrorsAbstract(HttpServletRequest request) {
		Locale locale;
		WebApplicationContext webApplicationContext = RequestContextUtils.findWebApplicationContext(request);
		if (webApplicationContext != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			this.messageSource = webApplicationContext;
			if (localeResolver != null) {
				locale = localeResolver.resolveLocale(request);
				this.locale = locale;
			}
		}
	}



	/**
	 * 构造器
	 * 
	 * @param messageSource
	 * @param locale
	 */
	public WebErrorsAbstract(MessageSource messageSource, Locale locale) {
		this.messageSource = messageSource;
		this.locale = locale;
	}

	public String getMessage(String code, Object... args) {
		if (messageSource == null) {
			throw new IllegalStateException("MessageSource cannot be null.");
		}
		return messageSource.getMessage(code, args, locale);
	}

	/**
	 * 添加错误代码
	 * 
	 * @param code
	 *            错误代码
	 * @param args
	 *            错误参数
	 * @see org.springframework.context.MessageSource#getMessage
	 */
	public void addErrorCode(String code, Object... args) {
		getErrors().add(getMessage(code, args));
	}

	/**
	 * 添加错误代码
	 * 
	 * @param code
	 *            错误代码
	 * @see org.springframework.context.MessageSource#getMessage
	 */
	public void addErrorCode(String code) {
		getErrors().add(getMessage(code));
	}

	/**
	 * 添加错误字符串
	 * 
	 * @param error
	 */
	public void addErrorString(String error) {
		getErrors().add(error);
	}

	/**
	 * 添加错误，根据MessageSource是否存在，自动判断为code还是string。
	 * 
	 * @param error
	 */
	public void addError(String error) {
		// if messageSource exist
		if (messageSource != null) {
			error = messageSource.getMessage(error, null, error, locale);
		}
		getErrors().add(error);
	}

	/**
	 * 是否存在错误
	 * 
	 * @return
	 */
	public boolean hasErrors() {
		return errors != null && errors.size() > 0;
	}

	/**
	 * 错误数量
	 * 
	 * @return
	 */
	public int getCount() {
		return errors == null ? 0 : errors.size();
	}

	/**
	 * 错误列表
	 * 
	 * @return
	 */
	public List<String> getErrors() {
		if (errors == null) {
			errors = new ArrayList<String>();
		}
		return errors;
	}

	/**
	 * 将错误信息保存至ModelMap，并返回错误页面。
	 * 
	 * @param model
	 * @return 错误页面地址
	 * @see org.springframework.ui.ModelMap
	 */
	public String showErrorPage(ModelMap model) {
		toModel(model);
		return getErrorPage();
	}

	/**
	 * 将错误信息保存至ModelMap
	 * 
	 * @param model
	 */
	public void toModel(Map<String, Object> model) {
		Assert.notNull(model);
		if (hasErrors()) {
			//throw new IllegalStateException("no errors found!");
			model.put(getErrorAttrName(), getErrors());
		}
	}

	public  boolean ifNull(Object o, String field) {
		if (o == null) {
			addErrorCode("error.required", field);
			return true;
		} else {
			return false;
		}
	}

	public boolean ifEmpty(Object[] o, String field) {
		if (o == null || o.length <= 0) {
			addErrorCode("error.required", field);
			return true;
		} else {
			return false;
		}
	}

	public boolean ifBlank(String s, String field, int maxLength) {
		if (StringUtils.isBlank(s)) {
			addErrorCode("error.required", field);
			return true;
		}
		if (ifMaxLength(s, field, maxLength)) {
			return true;
		}
		return false;
	}

	public boolean ifMaxLength(String s, String field, int maxLength) {
		if (s != null && s.length() > maxLength) {
			addErrorCode("error.maxLength", field, maxLength);
			return true;
		}
		return false;
	}

	public boolean ifOutOfLength(String s, String field, int minLength,
			int maxLength) {
		if (s == null) {
			addErrorCode("error.required", field);
			return true;
		}
		int len = s.length();
		if (len < minLength || len > maxLength) {
			addErrorCode("error.outOfLength", field, minLength, maxLength);
			return true;
		}
		
		return false;
	}

	public boolean ifNotEmail(String email, String field,int minLength, int maxLength) {
		if (ifOutOfLength(email, field,minLength, maxLength)) {
			return true;
		}
		Matcher m = EMAIL_PATTERN.matcher(email);
		if (!m.matches()) {
			addErrorCode("error.email", field);
			return true;
		}
		return false;
	}
	/**
	 * 是否为邮箱格式
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		if (email == null) 
			return false;
		Matcher m = EMAIL_PATTERN.matcher(email);
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	public boolean ifNotUsername(String username, String field, int minLength,
			int maxLength) {
		if (ifOutOfLength(username, field, minLength, maxLength)) {
			return true;
		}
		Matcher m = USERNAME_PATTERN.matcher(username);
		if (!m.matches()) {
			addErrorCode("error.username", field);
			return true;
		}
		return false;
	}
	
	public boolean ifNotRealname(String username, String field, int minLength,
			int maxLength) {
		if (ifOutOfLength(username, field, minLength, maxLength)) {
			return true;
		}
		Matcher m = REALNAME_PATTERN.matcher(username);
		if (!m.matches()) {
			addErrorCode("error.username", field);
			return true;
		}
		return false;
	}

	public boolean ifNotNumber(String number,String field, int minLength,
			int maxLength){
		if (ifOutOfLength(number, field, minLength, maxLength)) {
			return true;
		}
		Matcher m = NUMBER_PATTERN.matcher(number);
		if (!m.matches()) {
			addErrorCode("error.number", field);
			return true;
		}
		return false;
	}
	
	public boolean ifNotPhone(String phone,String field, int minLength,
			int maxLength){
		if (ifOutOfLength(phone, field, minLength, maxLength)) {
			return true;
		}
		Matcher m = PHONE_PATTERN.matcher(phone);
		if (!m.matches()) {
			addErrorCode("error.phone", field);
			return true;
		}
		return false;
	}
	
	public boolean ifNotLetter(String letter,String field, int minLength,
			int maxLength){
		if (ifOutOfLength(letter, field, minLength, maxLength)) {
			return true;
		}
		Matcher m = LETTER_PATTERN.matcher(letter);
		if (!m.matches()) {
			addErrorCode("error.letter", field);
			return true;
		}
		return false;
	}
	public boolean ifNotExist(Object o, Class<?> clazz, Serializable id) {
		if (o == null) {
			addErrorCode("error.notExist", clazz.getSimpleName(), id);
			return true;
		} else {
			return false;
		}
	}

	public void noPermission(Class<?> clazz, Serializable id) {
		addErrorCode("error.noPermission", clazz.getSimpleName(), id);
	}

	private MessageSource messageSource;
	private Locale locale;
	private List<String> errors;

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 获得本地化信息
	 * 
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * 设置本地化信息
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * 获得错误页面的地址
	 * 
	 * @return
	 */
	protected abstract String getErrorPage();

	/**
	 * 获得错误参数名称
	 * 
	 * @return
	 */
	protected abstract String getErrorAttrName();
}
