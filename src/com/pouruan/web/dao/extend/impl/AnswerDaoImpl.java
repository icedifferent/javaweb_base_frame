package com.pouruan.web.dao.extend.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.extend.AnswerDao;
import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Question;

@Repository
public class AnswerDaoImpl implements AnswerDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	
	@Override
	public boolean addAnswer(Answer answer) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			System.out.println("AnswerDao");
			session.save(answer);
		}catch(Exception e){
			logger.error("增加answer出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public Boolean deleteAnswer(Answer answer) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(answer);
		}catch(Exception e){
			logger.error("删除answer出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Answer> listAnswerByCondition(Question question, int pageNo, int pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(question.getId()!=null){
			condition+=" And p.question.id = :id";
		}
		String hql ="";
		hql = "FROM Answer AS p WHERE "+condition+" order by p.create_time  desc";
		Query<?> query =session.createQuery(hql);
		if(question.getId()!=null){
			query.setParameter("id",question.getId());
		}

		try{
			return (List<Answer>) query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getCountByCondition(Question question) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(question.getId()!=null){
			condition+=" And p.question.id = :id";
		}
		String hql = "select count(*)  FROM Answer AS p WHERE "+condition;
		Query<?> query =session.createQuery(hql);
		if(question.getId()!=null){
			query.setParameter("id",question.getId());
		}
		
		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public List<Answer> listTakeAnswerByCondition(Question question, int pageNo, int pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(question.getAnswer_token()!=null) {
			condition+=" And p.id = :id";
		}
		String hql ="";
		hql = "FROM Answer AS p WHERE "+condition+" order by p.create_time  desc";
		Query<?> query =session.createQuery(hql);
		if(question.getAnswer_token()!=null) {
			query.setParameter("id",question.getAnswer_token());
		}

		try{
			return (List<Answer>) query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

}
