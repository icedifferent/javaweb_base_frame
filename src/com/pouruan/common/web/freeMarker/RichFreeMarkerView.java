package com.pouruan.common.web.freeMarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * ��չspring��FreemarkerView������base���ԡ�
 * 
 * ֧��jsp��ǩ��Application��Session��Request��RequestParameters����
 * 
 * @author liufang
 * 
 */
public class RichFreeMarkerView extends FreeMarkerView {
	/**
	 * ����·����������
	 */
	public static final String CONTEXT_PATH = "base";

	/**
	 * ��model�����Ӳ���·��base�����㴦����·�����⡣
	 */
	@SuppressWarnings("unchecked")
	protected void exposeHelpers(Map model, HttpServletRequest request)
			throws Exception {
		super.exposeHelpers(model, request);
		model.put(CONTEXT_PATH, request.getContextPath());
	}
}