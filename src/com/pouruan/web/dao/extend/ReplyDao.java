package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Reply;

public interface ReplyDao {
	/**
	 * 增加回复
	 * @param reply
	 * @return boolean
	 */
	public boolean addReply(Reply reply)  throws Exception ;
	
	
	/**
	 * 删除回复
	 * @param reply
	 * @return boolean
	 */
	public boolean delReply(Reply reply)  throws Exception ;
	
	/**
	 * 根据条件列出回复
	 * @param reply
	 * @param pageNo
	 * @param pageSize
	 * @return List<Reply>
	 */ 
	public List<Reply> listReplyByCondition(Reply reply ,int pageNo,int pageSize)  throws Exception ;
	
	
	/**
	 * 查询符合条件的回复数目
	 * @param reply
	 * @return int
	 */
	public int getCountByCondition(Reply reply)  throws Exception ;
}
