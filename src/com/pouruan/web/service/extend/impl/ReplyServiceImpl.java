package com.pouruan.web.service.extend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.extend.ReplyDao;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;
import com.pouruan.web.service.extend.ReplyService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyDao replyDao;
	@Override
	public boolean addReply(Post post, String content,User replyUser) throws Exception {
		post.setLastRespondTime(TimeUtils.getCurrentDay());
		Reply reply=new Reply(content,post,replyUser);
		List<Reply> list=replyDao.listReplyByCondition(reply, 1, 1);
		if(list.size()!=0){
			if(System.currentTimeMillis()-list.get(0).getDate().getTime()<1000*10){
				return false;//发帖时间较短
			}
		}
		return replyDao.addReply(reply);
	}

	@Override
	public boolean delReply(int replyId) throws Exception {
		Reply reply=new Reply();
		reply.setId(replyId);
		List<Reply> replyList=replyDao.listReplyByCondition(reply, 1, 1);
		if(replyList.size()!=0){
			reply=replyList.get(0);
			return replyDao.delReply(reply);
		}else{
			return false;
		}
	}

	@Override
	public List<Reply> listReplyByCondition(Reply reply, int pageNo,
			int pageSize) throws Exception {
		List<Reply> replyList=replyDao.listReplyByCondition(reply, pageNo, pageSize);
		return replyList;
	}

	@Override
	public int getCountByCondition(Reply reply) throws Exception {
		return replyDao.getCountByCondition(reply);
	}

}
