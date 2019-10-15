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
	 * Ĭ�ϳɹ���Ϣ��������
	 */
	public static final String SUCCESS_ATTR_NAME = "successes";
	/**
	 * ͨ��HttpServletRequest����WebErrors
	 * 
	 * @param request
	 *            ��request�л��MessageSource��Locale��������ڵĻ���
	 * @return ���LocaleResolver�����򷵻ع��ʻ�WebErrors
	 */
	public static WebSuccess create(HttpServletRequest request) {
		return new WebSuccess(request);
	}
	public WebSuccess() {
	}
	/**
	 * ͨ��HttpServletRequest����WebErrors
	 * 
	 * @param request
	 *            ��request�л��MessageSource��Locale��������ڵĻ���
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
	 * ���ɹ���Ϣ������ModelMap
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
	 * ��ӳɹ�����
	 * 
	 * @param code
	 *            �ɹ�����
	 * @param args
	 *            �ɹ�����
	 * @see org.springframework.context.MessageSource#getMessage
	 */
	public void addSuccessCode(String code, Object... args) {
		getSuccess().add(getMessage(code, args));
	}

	/**
	 * ��ӳɹ�����
	 * 
	 * @param code
	 *            �ɹ�����
	 * @see org.springframework.context.MessageSource#getMessage
	 */
	public void addSuccessCode(String code) {
		getSuccess().add(getMessage(code));
	}

	/**
	 * ��ӳɹ��ַ���
	 * 
	 * @param error
	 */
	public void addSuccessString(String success) {
		getSuccess().add(success);
	}
	/**
	 * ��ӳɹ�������MessageSource�Ƿ���ڣ��Զ��ж�Ϊcode����string��
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
	 * �Ƿ���ڳɹ���Ϣ
	 * 
	 * @return
	 */
	public boolean hasSuccess() {
		return success != null && success.size() > 0;
	}
	/**
	 * �ɹ�����
	 * 
	 * @return
	 */
	public int getCount() {
		return success == null ? 0 : success.size();
	}
	/**
	 * �ɹ��б�
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
	 * ��ñ��ػ���Ϣ
	 * 
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * ���ñ��ػ���Ϣ
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
