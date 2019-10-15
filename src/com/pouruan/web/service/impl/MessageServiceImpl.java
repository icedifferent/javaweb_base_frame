package com.pouruan.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.MessageDao;
import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.MessageService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	private MessageDao messageDao;
	@Override
	public boolean addMessage(String content, User fromUser, User toUserId,int type) throws Exception {
		//检测发送时间间隔
		Message myMessage=new Message();
		myMessage.setFromUser(fromUser);
		List<Message> list =getMessageByCondition(myMessage,1,1);
		if(list.size()!=0){
			long millTime=list.get(0).getDate().getTime();
			System.out.println(millTime);
			if(System.currentTimeMillis()-millTime<10*1000){//10s
				return false;//发送时间间隔过短
			}
		}
		Message message =new Message(content,fromUser,toUserId,type);
		return messageDao.addMessage(message);
	}
	
	
	@Override
	public List<Message> getMessageByCondition(Message message, int pageNo,
			int pageSize) throws Exception {
		return messageDao.getMessageByCondition(message, pageNo, pageSize);
	}


	@Override
	public int getMessageCountByCondition(Message message) throws Exception {
		return messageDao.getMessageCountByCondition(message);
	}


	@Override
	public boolean editMessage(Message message) {
		// TODO Auto-generated method stub
		return false;
	}


}
