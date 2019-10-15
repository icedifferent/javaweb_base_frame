package com.pouruan.common.web.tips;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class WebSuccess {
	private MessageSource messageSource;
	private List<String> success;
	private Locale locale;
	/**
	 * 默认成功信息属性名称
	 */
	public static final String SUCCESS_ATTR_NAME = "successes";
	/**
	 * 通过HttpServletRequest创建WebErrors
	 * 
	 * @param request
	 *            从request中获得MessageSource和Locale，如果存在的话。
	 * @return 如果LocaleResolver存在则返回国际化WebErrors
	 */
	public static WebSuccess create(HttpServletRequest request) {
		return new WebSuccess(request);
	}
	public WebSuccess() {
	}
	/**
	 * 通过HttpServletRequest创建WebErrors
	 * 
	 * @param request
	 *            从request中获得MessageSource和Locale，如果存在的话。
	 */
	public WebSuccess(HttpServletRequest request) {
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

	public String getMessage(String code, Object... args) {
		if (messageSource == null) {
			throw new IllegalStateException("MessageSource cannot be null.");
		}
		return messageSource.getMessage(code, args, locale);
	}
	/**
	 * 将成功信息保存至ModelMap
	 * 
	 * @param model
	 */
	public void toModel(Map<String, Object> model) {
		Assert.notNull(model);
		if (hasSuccess()) {
			//throw new IllegalStateException("no success found!");
			model.put(getSuccessAttrName(), getSuccess());
		}
		
	}
	protected String getSuccessAttrName() {
		return SUCCESS_ATTR_NAME;
	}
	/**
	 * 添加成功代码
	 * 
	 * @param code
	 *            成功代码
	 * @param args
	 *            成功参数
	 * @see org.springframework.context.MessageSource#getMessage
	 */
	public void addSuccessCode(String code, Object... args) {
		getSuccess().add(getMessage(code, args));
	}

	/**
	 * 添加成功代码
	 * 
	 * @param code
	 *            成功代码
	 * @see org.springframework.context.MessageSource#getMessage
	 */
	public void addSuccessCode(String code) {
		getSuccess().add(getMessage(code));
	}

	/**
	 * 添加成功字符串
	 * 
	 * @param error
	 */
	public void addSuccessString(String success) {
		getSuccess().add(success);
	}
	/**
	 * 添加成功，根据MessageSource是否存在，自动判断为code还是string。
	 * 
	 * @param error
	 */
	public void addSuccess(String success) {
		// if messageSource exist
		if (messageSource != null) {
			success = messageSource.getMessage(success, null, success, locale);
		}
		getSuccess().add(success);
	}
	/**
	 * 是否存在成功信息
	 * 
	 * @return
	 */
	public boolean hasSuccess() {
		return success != null && success.size() > 0;
	}
	/**
	 * 成功数量
	 * 
	 * @return
	 */
	public int getCount() {
		return success == null ? 0 : success.size();
	}
	/**
	 * 成功列表
	 * 
	 * @return
	 */
	public List<String> getSuccess() {
		if (this.success == null) {
			this.success = new ArrayList<String>();
		}
		return this.success;
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

}
