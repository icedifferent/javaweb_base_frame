package com.pouruan.web.service;
import java.util.List;

import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.User;
public interface MessageService {
	/**
	 * ������Ϣ
	 * @param content
	 * @param fromUser
	 * @param toUser
	 * @param type
	 * @return boolean
	 */
	public boolean addMessage(String content,User fromUser,User toUser,int type)  throws Exception;
	
	
	
	/**
	 * �г�ĳ�˷��͵���Ϣ
	 * @param message
	 * @param pageNo
	 * @param pageSize
	 * @return List<Message>
	 */
	public  List<Message> getMessageByCondition(Message message,int pageNo,int  pageSize) throws Exception;
	
	
	/**
	 * ����������ȡ��Ϣ������
	 * @param message
	 * @return int
	 */
	public int getMessageCountByCondition(Message message) throws Exception;
	
	
	/**
	 * �޸���Ϣ
	 * @param message
	 * @return boolean
	 */
	public boolean editMessage(Message message) throws Exception;
}
