package com.pouruan.web.service.aopService;

import org.aspectj.lang.ProceedingJoinPoint;
/**
 * ȫ��ACTION�г��û���ص���Ϣ
 * @author ICE
 *
 */
public interface UserAopService {
	public Object showUserInfo(ProceedingJoinPoint pjp);
}
