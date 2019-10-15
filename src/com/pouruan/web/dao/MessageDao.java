package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Message;

public interface MessageDao {
	/**
	 * ���ݶ��������г�������Ϣ
	 * @param message
	 * @param pageNo
	 * @param pageSize
	 * @return List<Message>
	 */
	public List<Message> getMessageByCondition(Message message,int pageNo,int  pageSize)throws Exception ;
	
	
	/**
	 * ɾ����Ϣ
	 * @param message
	 * @return boolean
	 */
	public boolean delMessage(Message message)throws Exception ;
	
	/**
	 * ������Ϣ
	 * @param message
	 * @return boolean
	 */
	public boolean addMessage(Message message)throws Exception ;
	
	
	/**
	 * ����������ȡ��Ϣ������
	 * @param message
	 * @return int
	 */
	public int getMessageCountByCondition(Message message)throws Exception  ;
	
	/**
	 * �޸���Ϣ
	 * @param message
	 * @return boolean
	 */
	public boolean editMessage(Message message)throws Exception ;
}
