package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;

public interface ReplyService {
	/**
	 * ���ӻظ�
	 * @param post
	 * @param content
	 * @param replyUser
	 * @return boolean
	 */
	public boolean addReply(Post post,String content,User replyUser) throws Exception;
	
	
	/**
	 * ɾ���ظ�
	 * @param replyId
	 * @return boolean
	 */
	public boolean delReply(int replyId) throws Exception;
	
	/**
	 * ���������г��ظ�
	 * @param reply
	 * @param pageNo
	 * @param pageSize
	 * @return List<Reply>
	 */
	public List<Reply> listReplyByCondition(Reply reply,int pageNo,int pageSize) throws Exception;
	
	/**
	 * �ҳ��������������Ӹ���
	 * @param reply
	 * @return int
	 */
	public int  getCountByCondition(Reply reply) throws Exception;
}
