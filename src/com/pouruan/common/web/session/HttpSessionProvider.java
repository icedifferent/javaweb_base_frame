package com.pouruan.common.web.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * HttpSession�ṩ��
 * 
 * @author liufang
 * 
 */
public class HttpSessionProvider implements SessionProvider {

	public Serializable getAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (Serializable) session.getAttribute(name);
		} else {
			return null;
		}
	}

	//sessionĬ��ʱ��20���� ��web.xml����
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
//		System.out.println( session.getMaxInactiveInterval());
//		Enumeration<String> names = session.getAttributeNames();
//		for(Enumeration e=names;e.hasMoreElements();){
//			 
//		       String thisName=e.nextElement().toString();
//		       System.out.println(thisName+"--------------"+session.getAttribute(thisName));
//		 
//		}
	}

	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		return request.getSession().getId();
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
}
