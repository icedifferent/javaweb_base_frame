package com.pouruan.web.dao.extend.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.extend.MusicDao;
import com.pouruan.web.entity.extend.Music;
@Repository
public class MusicDaoImpl implements MusicDao {
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean addMusic(Music music) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(music);
		}catch(Exception e){
			logger.error("增加music出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean editMusic(Music music) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(music);
		}catch(Exception e){
			logger.error("修改music出错"+e);
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public List<Music> listMusicByCondition(Music music,int pageNo,int pageSize) throws Exception {
		String condition ="1=1 ";
		if(music.getId()!=null){
			condition+=" And m.id ="+music.getId();
		}
		if(music.getName()!=null){
			condition+=" And m.name  like '%"+music.getName()+"%'";
		}
		if(music.getPlayer()!=null){
			condition+=" And m.player  like '%"+music.getPlayer()+"%'";
		}
		String hql = "FROM Music AS m WHERE "+condition +"order by m.id desc";
		Session session= sessionFactory.getCurrentSession();
		Query<?> query =session.createQuery(hql);
		 
		try{
			return (List<Music>) query.setFirstResult((pageNo-1)*pageSize)  
			        .setMaxResults(pageSize)  
			        .list();
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public int getMusicCountByCondition(Music music) throws Exception {
		String condition ="1=1 ";
		if(music.getId()!=null){
			condition+=" And m.id ="+music.getId();
		}
		if(music.getName()!=null){
			condition+=" And m.name  like '%"+music.getName()+"%'";
		}
		if(music.getPlayer()!=null){
			condition+=" And m.player  like '%"+music.getPlayer()+"%'";
		}
		String hql = "select count(*) FROM Music AS m WHERE "+condition;
		Session session= sessionFactory.getCurrentSession();
		Query<?> query =session.createQuery(hql);
		
		try{
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public boolean delMusic(Music music) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.delete(music);
		}catch(Exception e){
			logger.error("删除出错"+e);
			throw new Exception(e);
		}
		return true;
	}

}
