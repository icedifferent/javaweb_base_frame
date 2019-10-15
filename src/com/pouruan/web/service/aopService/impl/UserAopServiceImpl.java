package com.pouruan.web.service.aopService.impl;


import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;








import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.MessageService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.extend.MarkService;
import com.pouruan.web.service.security.CommonService;
import org.springframework.ui.ModelMap;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Component
@Aspect  
public class UserAopServiceImpl {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MarkService markService;
	
	@Pointcut("(execution(* com.pouruan.web.action.*.*(..)))||(execution(* com.pouruan.web.action.*.*.*(..)))")
	public void pointCut_(){}//切入点
	@Around("pointCut_()")
	public Object showUserInfo(ProceedingJoinPoint pjp) throws Throwable{
	    Object[] args = pjp.getArgs();
	       for(Object obj : args){
	           if(obj instanceof ModelMap){
					User user=commonService.getCustomUser();
					if(user!=null){
						Message message=new Message();
						message.setToUser(user);
						message.setType((byte)0);
						List messageList=messageService.getMessageByCondition(message, 1, 3);
						message.setIfRead((byte)0);//未读消息条数
						int unReadMessageCount=messageService.getMessageCountByCondition(message);
						message.setType((byte)1);
						List atMessageList=messageService.getMessageByCondition(message, 1, 3);
						int unAtReadMessageCount=messageService.getMessageCountByCondition(message);
						int isSign = markService.isSign(user.getUserId());
						((ModelMap)obj).addAttribute("aopUserInfo",user);
						((ModelMap)obj).addAttribute("aopMessageList",messageList);
						((ModelMap)obj).addAttribute("aopUnReadMessageCount",unReadMessageCount);
						((ModelMap)obj).addAttribute("aopAtMessageList",atMessageList);
						((ModelMap)obj).addAttribute("aopUnAtReadMessageCount",unAtReadMessageCount);
						
						((ModelMap)obj).addAttribute("aopisSign",isSign);
					}
	           }
	     }
		return pjp.proceed(args);
	}
}
