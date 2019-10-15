package com.pouruan.web.service.aopService;

import org.aspectj.lang.ProceedingJoinPoint;
/**
 * 全局ACTION列出用户相关的信息
 * @author ICE
 *
 */
public interface UserAopService {
	public Object showUserInfo(ProceedingJoinPoint pjp);
}
