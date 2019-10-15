package com.pouruan.web.component;




import static com.pouruan.common.web.Constants.AUTH_KEY;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pouruan.common.web.CookieUtils;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.AuthService;




/**
 * 
 * �û�����������
 * @author ICE
 *
 */
public class UserActionInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger(UserActionInterceptor.class);
	public static final String PROCESS_URL = "processUrl";
	public static final String RETURN_URL = "returnUrl";
	private String loginUrl;
	private String processUrl;
	private String returnUrl;
	@Autowired
	private AuthService authService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// ����û�
		Cookie authKeyCookie=CookieUtils.getCookie(request, AUTH_KEY);
		User user=null;
		if(authKeyCookie!=null){//������֤��Ϣ
			String authKey = authKeyCookie.getValue();
			user=authService.getUserByKey(authKey);
		}
		if(user!=null){//��֤��Ϣ��Ч
			logger.debug("��֤��Ϣ��Ч");
		}else{
			logger.debug("��֤��Ϣ��Ч");
		}
		// �û�Ϊnull��ת����½ҳ��
		if (user == null) {
			response.sendRedirect(getLoginUrl(request));
			return false;
		}
		//ʹ���̳߳ر����û���Ϣ

		return true;
	}

	
	/**
	 * ��ȡ��¼������
	 * @param request
	 * @return
	 */
	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl).append("?");
		buff.append(RETURN_URL).append("=").append(returnUrl);
		if (!StringUtils.isBlank(processUrl)) {
			buff.append("&").append(PROCESS_URL).append("=").append(
					getProcessUrl(request));
		}
		return buff.toString();
	}
	
	
	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
