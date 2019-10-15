package com.pouruan.web.service.aopService.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;









import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;   
import org.springframework.web.context.request.ServletRequestAttributes;  

import com.pouruan.common.web.RequestUtils;
import com.pouruan.web.service.LogService;
import com.pouruan.web.service.aopService.LogAopService;

/*
 *���� aspectjweaver.jar	aspectjrt.jar		
 */
/**
 * ��־��¼������
 * @author Ice
 *
 */
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//��������
@Component
@Aspect  // ָ����ǰ��Ϊ������
public class LogAopServiceImpl implements LogAopService{
	@Autowired
	private LogService logService;
	@Pointcut("(execution(* com.pouruan.web.action.AdminAction.*(..)))&&(!execution(* com.pouruan.web.action.AdminAction.showLog(..)))")
	public void pointCut_(){}//�����
	@Before("pointCut_()")
	@Override
	public void writeLog(JoinPoint joinPoint) throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
		String classType = joinPoint.getTarget().getClass().getName();  
        Class<?> clazz = null;
		try {
			clazz = Class.forName(classType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		String methodName=joinPoint.getSignature().getName();
		String log="classType:"+clazz + " methodName:"+methodName;
		//��ȡ������Ϣ
		Map<String, Object> map=RequestUtils.getQueryParams(request);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			log=log+";" +entry.getKey()+":"+entry.getValue();
		}
		String url=RequestUtils.getLocation(request);
		logService.wirteLog(url, log);
	}
}
