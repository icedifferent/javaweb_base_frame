package com.pouruan.web.dao.extend.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.extend.ReplyDao;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;
@Repository
public class ReplyDaoImpl implements ReplyDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean addReply(Reply reply) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(reply);
		}catch(Exception e){
			logger.error("增加reply出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean delReply(Reply reply) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(reply);
		}catch(Exception e){
			logger.error("删除reply出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Reply> listReplyByCondition(Reply reply, int pageNo,
			int pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(reply.getId()!=null){
			condition+=" And r.id = :id";
		}
		if(reply.getPost()!=null){
			condition+=" And r.post = :post";
		}
		if(reply.getReplyUser()!=null){
			condition+=" And r.replyUser = :replyUser";
		}
		String hql = "FROM Reply AS r WHERE "+condition+" order by r.id  desc";
		Query<?> query =session.createQuery(hql);
		if(reply.getId()!=null){
			query.setParameter("id",reply.getId());
		}
		if(reply.getPost()!=null){
			query.setParameter("post",reply.getPost());
		}
		if(reply.getReplyUser()!=null){
			query.setParameter("replyUser",reply.getReplyUser());
		}
		
		try{
			return (List<Reply>) query.setFirstResult((pageNo-1)*pageSize)  
			        .setMaxResults(pageSize)  
			        .list();  
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getCountByCondition(Reply reply) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(reply.getId()!=null){
			condition+=" And r.id = :id";
		}
		if(reply.getPost()!=null){
			condition+=" And r.post = :post";
		}
		if(reply.getReplyUser()!=null){
			condition+=" And r.replyUser = :replyUser";
		}
		String hql = "select count(*)  FROM Reply AS r WHERE "+condition;
		Query<?> query =session.createQuery(hql);
		if(reply.getId()!=null){
			query.setParameter("id",reply.getId());
		}
		if(reply.getPost()!=null){
			query.setParameter("post",reply.getPost());
		}
		if(reply.getReplyUser()!=null){
			query.setParameter("replyUser",reply.getReplyUser());
		}
		 
		try{
			return ((Number)query.uniqueResult()).intValue();
		}catch(Exception e){
			throw new Exception(e);
		}
	}

}
