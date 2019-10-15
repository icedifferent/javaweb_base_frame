package com.pouruan.web.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.MessageDao;
import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.User;

@Repository
public class MessageDaoImpl implements MessageDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public List<Message> getMessageByCondition(Message message,int pageNo,int  pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(message.getMessageId()!=null){
			condition+=" And m.messageId = :messageId";
		}
		if(message.getFromUser()!=null){
			condition+=" And m.fromUser = :fromUser";
		}
		if(message.getToUser()!=null){
			condition+=" And m.toUser = :toUser";
		}
		if(message.getIfRead()!=null){
			condition+=" And m.ifRead = :ifRead";
		}
		if(message.getType()!=null){
			condition+=" And m.type = :type";
		}
		String hql = "FROM Message AS m WHERE "+condition+" order by m.messageId  desc";
		Query<?> query =session.createQuery(hql);
		if(message.getMessageId()!=null)
			query.setParameter("messageId", message.getMessageId());
		if(message.getFromUser()!=null)
			query.setParameter("fromUser", message.getFromUser());
		if(message.getToUser()!=null)
			query.setParameter("toUser", message.getToUser());
		if(message.getIfRead()!=null)
			query.setParameter("ifRead", message.getIfRead());
		if(message.getType()!=null)
			query.setParameter("type", message.getType());
		
		try{
			return (List<Message>) query.setFirstResult((pageNo-1)*pageSize)  
			        .setMaxResults(pageSize)  
			        .list(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	
	
	@Override
	public int getMessageCountByCondition(Message message) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(message.getMessageId()!=null){
			condition+=" And m.messageId = :messageId";
		}
		if(message.getFromUser()!=null){
			condition+=" And m.fromUser = :fromUser";
		}
		if(message.getToUser()!=null){
			condition+=" And m.toUser = :toUser";
		}
		if(message.getIfRead()!=null){
			condition+=" And m.ifRead = :ifRead";
		}
		if(message.getType()!=null){
			condition+=" And m.type = :type";
		}
		String hql = "select count(*) FROM  Message AS m  WHERE "+condition;
		Query<?> query =session.createQuery(hql);
		if(message.getMessageId()!=null)
			query.setParameter("messageId", message.getMessageId());
		if(message.getFromUser()!=null)
			query.setParameter("fromUser", message.getFromUser());
		if(message.getToUser()!=null)
			query.setParameter("toUser", message.getToUser());
		if(message.getIfRead()!=null)
			query.setParameter("ifRead", message.getIfRead());
		if(message.getType()!=null)
			query.setParameter("type", message.getType());

		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}
	@Override
	public boolean delMessage(Message message) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(message);
		}catch(Exception e){
			logger.error("删除message出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean addMessage(Message message) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(message);
		}catch(Exception e){
			logger.error("增加message出错"+e);
			throw new Exception(e);
		}
		return true;
	}



	@Override
	public boolean editMessage(Message message) {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(message);
		}catch(Exception e){
			logger.error("修改message出错"+e);
			return false;
		}
		return true;
	}

}
