package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Reply;

public interface ReplyDao {
	/**
	 * ���ӻظ�
	 * @param reply
	 * @return boolean
	 */
	public boolean addReply(Reply reply)  throws Exception ;
	
	
	/**
	 * ɾ���ظ�
	 * @param reply
	 * @return boolean
	 */
	public boolean delReply(Reply reply)  throws Exception ;
	
	/**
	 * ���������г��ظ�
	 * @param reply
	 * @param pageNo
	 * @param pageSize
	 * @return List<Reply>
	 */ 
	public List<Reply> listReplyByCondition(Reply reply ,int pageNo,int pageSize)  throws Exception ;
	
	
	/**
	 * ��ѯ���������Ļظ���Ŀ
	 * @param reply
	 * @return int
	 */
	public int getCountByCondition(Reply reply)  throws Exception ;
}
