package com.pouruan.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.tips.WebSuccess;
import com.pouruan.web.component.ValidateSubmit;
import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.MessageService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.security.CommonService;

@Controller
public class MessageAction {
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired 
	private CommonService commonService;
	@Autowired
	private ValidateSubmit  vaildateSubmit;
	private Logger logger = LogManager.getRootLogger();
	
	/**
	 * test
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/t.do")
	public void listMessage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
	/*	//insert new one
		String content="test";
	//	int fromUserId=3;
		//int toUserId=4;
		//messageService.addMessage(StringEscapeUtils.escapeHtml4(content), fromUserId, toUserId,0);
		
		//find out the ten latest
		int pageNo=1;
		int pageSize=10;
		Message m=new Message();
		m.setIfRead((byte)0);
		List<Message> list =messageService.getMessageByCondition(m, pageNo, pageSize);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getFromUser()!=null){
				System.out.println("fromUser not null");
			}else{
				System.out.println("null");
			}
		}*/
	}
	
	/**
	 * 列出用户站内消息
	 * @param message
	 * @param sended
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/User/Message/listMessage.do")
	public String listMessage(Message message,String sended,Integer pageNo,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		if(pageNo==null||pageNo<0){pageNo=1;}
		Map<String, String> RequestParameters=new HashMap<String, String>();
		User users=commonService.getCustomUser();
		if(sended==null){
			message.setToUser(users);
		}else{
			RequestParameters.put("sended",sended);
			message.setFromUser(users);//用户发出的
		}
		List<Message> list =messageService.getMessageByCondition(message, pageNo, 10);
		int messageSize=messageService.getMessageCountByCondition(message);
		model.addAttribute("messageList",list);
		model.addAttribute("messageSize",messageSize);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("RequestParameters",RequestParameters);
		return "User/Message/listMessage";
	}
	
	/**
	 * 阅读消息
	 * @param messageId
	 * @param request
	 * @param response
	 * @param model
	 * @return  String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/User/Message/readMessage.do", method = RequestMethod.GET)
	public String readMessage(Integer messageId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		User user=commonService.getCustomUser();
		errors.ifNotNumber(Integer.toString(messageId), "messageId", 1, 10);
		if(!errors.hasErrors()){
			Message message=new Message();
			message.setMessageId(messageId);
			List list=messageService.getMessageByCondition(message, 1, 1);
			if(list.size()==0){
				errors.addError("消息不存在");
				logger.debug("消息不存在");
			}else{
				Message ms=(Message) list.get(0);
				if((ms.getFromUser().getUserId()!=user.getUserId())&&(ms.getToUser().getUserId()!=user.getUserId())){
					errors.addError("无权查看他人消息哦");
					logger.debug("无权查看他人消息哦");
				}else{
					if((ms.getToUser().getUserId()==user.getUserId())&&(ms.getIfRead()==0)){//修改阅读标记
						ms.setIfRead((byte)1);
						try {
							messageService.editMessage(ms);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							errors.addError("修改阅读量未知错误"+e.getMessage());
						}
					}
					model.addAttribute("message",ms);
				}
			}
		}
		errors.toModel(model);//输出错误信息
		return "User/Message/readMessage";
	}
	/**
	 * 发送站内消息界面
	 * @param toUserId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/User/Message/send.do", method = RequestMethod.GET)
	public String send(Integer toUserId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		if(toUserId!=null){
			User user=userService.getUserByUserId(toUserId);
			if(user!=null){
				model.addAttribute("toUserId",toUserId);
			}
		}
		return "User/Message/send";
	}
	
	
	
	/**
	 * 发送消息
	 * @param toUserId
	 * @param content
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/User/Message/send.do", method = RequestMethod.POST)
	public String sendDone(Integer toUserId,String content,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors;
		WebSuccess successes = WebSuccess.create(request);
		User toUser=userService.getUserByUserId(toUserId);
		errors=vaildateSubmit.validateMessage(content, toUserId, request, response);
		if(toUser==null){
			errors.addError("用户不存在");
		}
		if(!errors.hasErrors()){
			model.addAttribute("user",toUser);
			User users=commonService.getCustomUser();
			if(toUser.getUserId()==users.getUserId()){
				errors.addError("不能给自己发消息哦");
			}
			if(!errors.hasErrors()){
				try {
					if(messageService.addMessage(StringEscapeUtils.escapeHtml4(content), users, toUser,0)){
						successes.addSuccess("发送成功啦");
					}else{
						errors.addError("发送失败！发送时间间隔太短");
					}
				} catch (Exception e) {
					errors.addError("addMessage未知错误"+e);
				}
			}
		}
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		return "User/Message/send";
	}
}
