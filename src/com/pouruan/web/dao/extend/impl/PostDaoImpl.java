package com.pouruan.web.dao.extend.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.extend.PostDao;
import com.pouruan.web.entity.extend.Music;
import com.pouruan.web.entity.extend.Post;
@Repository
public class PostDaoImpl implements PostDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean addPost(Post post) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(post);
		}catch(Exception e){
			logger.error("增加post出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean editPost(Post post) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(post);
		}catch(Exception e){
			logger.error("修改post出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean delPost(Post post) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(post);
		}catch(Exception e){
			logger.error("删除post出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Post> listPostByCondition(Post post, int pageNo, int pageSize,int orderType) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(post.getId()!=null){
			condition+=" And p.id = :id";
		}
		if(post.getTitle()!=null){
			condition+=" And p.title like :title";
		}
		if(post.getUser()!=null){
			condition+=" And p.user = :user";
		}
		if(post.getContent()!=null){
			condition+=" And p.content like :content";
		}
		if(post.getModule()!=null){
			condition+=" And p.module = :module";
		}
		String hql ="";
		if(orderType==1)
			hql = "FROM Post AS p WHERE "+condition+" order by p.id  desc";
		else
			hql = "FROM Post AS p WHERE "+condition+" order by p.lastRespondTime  desc";
		Query<?> query =session.createQuery(hql);
		if(post.getId()!=null){
			query.setParameter("id",post.getId());
		}
		if(post.getTitle()!=null){
			query.setParameter("title","%"+post.getTitle()+"%");
		}
		if(post.getUser()!=null){
			query.setParameter("user",post.getUser());
		}
		if(post.getContent()!=null){
			query.setParameter("content","%"+post.getContent()+"%");
		}
		if(post.getModule()!=null){
			query.setParameter("module",post.getModule());
		}
		
		try{
			return (List<Post>) query.setFirstResult((pageNo-1)*pageSize)  
			        .setMaxResults(pageSize)  
			        .list(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getCountByCondition(Post post) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		String condition ="1=1 ";
		if(post.getId()!=null){
			condition+=" And p.id = :id";
		}
		if(post.getTitle()!=null){
			condition+=" And p.title like :title";
		}
		if(post.getUser()!=null){
			condition+=" And p.user = :user";
		}
		if(post.getContent()!=null){
			condition+=" And p.content like :content";
		}
		String hql = "select count(*)  FROM Post AS p WHERE "+condition;
		Query<?> query =session.createQuery(hql);
		if(post.getId()!=null){
			query.setParameter("id",post.getId());
		}
		if(post.getTitle()!=null){
			query.setParameter("title","%"+post.getTitle()+"%");
		}
		if(post.getUser()!=null){
			query.setParameter("user",post.getUser());
		}
		if(post.getContent()!=null){
			query.setParameter("content","%"+post.getContent()+"%");
		}
		
		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

}
