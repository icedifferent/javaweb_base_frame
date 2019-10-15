package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Message;

public interface MessageDao {
	/**
	 * 根据对象条件列出所有消息
	 * @param message
	 * @param pageNo
	 * @param pageSize
	 * @return List<Message>
	 */
	public List<Message> getMessageByCondition(Message message,int pageNo,int  pageSize)throws Exception ;
	
	
	/**
	 * 删除消息
	 * @param message
	 * @return boolean
	 */
	public boolean delMessage(Message message)throws Exception ;
	
	/**
	 * 增加消息
	 * @param message
	 * @return boolean
	 */
	public boolean addMessage(Message message)throws Exception ;
	
	
	/**
	 * 根据条件获取消息的数量
	 * @param message
	 * @return int
	 */
	public int getMessageCountByCondition(Message message)throws Exception  ;
	
	/**
	 * 修改消息
	 * @param message
	 * @return boolean
	 */
	public boolean editMessage(Message message)throws Exception ;
}
