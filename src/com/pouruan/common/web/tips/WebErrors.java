package com.pouruan.common.web.tips;

/**
 * webҳ�������
 * 
 */
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

public class WebErrors extends WebErrorsAbstract {
	/**
	 * Ĭ�ϴ���ҳ��
	 */
	public static final String ERROR_PAGE = "/common/bootError_message";
	/**
	 * Ĭ�ϴ�����Ϣ��������
	 */
	public static final String ERROR_ATTR_NAME = "errors";

	/**
	 * ͨ��HttpServletRequest����WebErrors
	 * 
	 * @param request
	 *            ��request�л��MessageSource��Locale��������ڵĻ���
	 * @return ���LocaleResolver�����򷵻ع��ʻ�WebErrors
	 */
	public static WebErrors create(HttpServletRequest request) {
		return new WebErrors(request);
	}

	public WebErrors() {
	}

	public WebErrors(HttpServletRequest request) {
		super(request);
	}

	/**
	 * ������
	 * 
	 * @param messageSource
	 * @param locale
	 */
	public WebErrors(MessageSource messageSource, Locale locale) {
		super(messageSource, locale);
	}

	@Override
	protected String getErrorAttrName() {
		return ERROR_ATTR_NAME;
	}

	@Override
	protected String getErrorPage() {
		return ERROR_PAGE;
	}
}
