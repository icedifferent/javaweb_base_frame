package com.pouruan.web.service;
import java.util.List;

import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.User;
public interface MessageService {
	/**
	 * 增加消息
	 * @param content
	 * @param fromUser
	 * @param toUser
	 * @param type
	 * @return boolean
	 */
	public boolean addMessage(String content,User fromUser,User toUser,int type)  throws Exception;
	
	
	
	/**
	 * 列出某人发送的消息
	 * @param message
	 * @param pageNo
	 * @param pageSize
	 * @return List<Message>
	 */
	public  List<Message> getMessageByCondition(Message message,int pageNo,int  pageSize) throws Exception;
	
	
	/**
	 * 根据条件获取消息的数量
	 * @param message
	 * @return int
	 */
	public int getMessageCountByCondition(Message message) throws Exception;
	
	
	/**
	 * 修改消息
	 * @param message
	 * @return boolean
	 */
	public boolean editMessage(Message message) throws Exception;
}
