package com.pouruan.web.service.aopService;

import org.aspectj.lang.JoinPoint;

public interface LogAopService {
	/**
	 * ��¼��־
	 * @param joinPoint
	 * 
	 */
	public void writeLog(JoinPoint joinPoint) throws Exception;
}
