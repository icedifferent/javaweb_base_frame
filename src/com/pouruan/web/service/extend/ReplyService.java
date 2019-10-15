package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;

public interface ReplyService {
	/**
	 * 增加回复
	 * @param post
	 * @param content
	 * @param replyUser
	 * @return boolean
	 */
	public boolean addReply(Post post,String content,User replyUser) throws Exception;
	
	
	/**
	 * 删除回复
	 * @param replyId
	 * @return boolean
	 */
	public boolean delReply(int replyId) throws Exception;
	
	/**
	 * 根据条件列出回复
	 * @param reply
	 * @param pageNo
	 * @param pageSize
	 * @return List<Reply>
	 */
	public List<Reply> listReplyByCondition(Reply reply,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 找出符合条件的帖子个数
	 * @param reply
	 * @return int
	 */
	public int  getCountByCondition(Reply reply) throws Exception;
}
