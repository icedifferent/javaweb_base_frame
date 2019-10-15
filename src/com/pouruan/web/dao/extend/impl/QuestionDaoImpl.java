package com.pouruan.web.dao.extend.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.extend.QuestionDao;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;

@Repository
public class QuestionDaoImpl implements QuestionDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	
	@Override
	public boolean addQuestion(Question question) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(question);
		}catch(Exception e){
			logger.error("增加question出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public Boolean deleteQuestion(Question question) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(question);
		}catch(Exception e){
			logger.error("删除question出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Question> listQuestionByCondition(Question question, int pageNo, int pageSize) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(question.getId()!=null){
			condition+=" And p.id = :id";
		}
		if(question.getTitle()!=null){
			condition+=" And p.title like :title";
		}
		if(question.getUser()!=null){
			condition+=" And p.user = :user";
		}
		if(question.getDiscribe()!=null){
			condition+=" And p.discribe like :discribe";
		}
		String hql ="";
		hql = "FROM Question AS p WHERE "+condition+" order by p.create_time  desc";
		Query<?> query =session.createQuery(hql);
		if(question.getId()!=null){
			query.setParameter("id",question.getId());
		}
		if(question.getTitle()!=null){
			query.setParameter("title","%"+question.getTitle()+"%");
		}
		if(question.getUser()!=null){
			query.setParameter("user",question.getUser());
		}
		if(question.getDiscribe()!=null){
			query.setParameter("discribe","%"+question.getDiscribe()+"%");
		}
		
		try{
			return (List<Question>) query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getCountByCondition(Question question) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(question.getId()!=null){
			condition+=" And p.id = :id";
		}
		if(question.getTitle()!=null){
			condition+=" And p.title like :title";
		}
		if(question.getUser()!=null){
			condition+=" And p.user = :user";
		}
		if(question.getDiscribe()!=null){
			condition+=" And p.discribe like :discribe";
		}
		String hql = "select count(*) FROM Question AS p WHERE "+condition;
		Query<?> query =session.createQuery(hql);
		if(question.getId()!=null){
			query.setParameter("id",question.getId());
		}
		if(question.getTitle()!=null){
			query.setParameter("title","%"+question.getTitle()+"%");
		}
		if(question.getUser()!=null){
			query.setParameter("user",question.getUser());
		}
		if(question.getDiscribe()!=null){
			query.setParameter("discribe","%"+question.getDiscribe()+"%");
		}
		
		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public boolean updateQuestion(Question question) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(question);
		}catch(Exception e){
			logger.error("修改question出错"+e);
			throw new Exception(e);
		}
		return true;
	}
	
}
