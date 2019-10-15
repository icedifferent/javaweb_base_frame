package com.pouruan.web.dao.extend.impl;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.extend.MarkDao;
import com.pouruan.web.entity.extend.Mark;

@Repository
public class MarkDaoImpl implements MarkDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addMark(Mark mark,Integer score) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		if(score >= 0) {
			Integer beforeadd = mark.getMark();// 原来的分数
			Integer afteradd = beforeadd + score;// 现在的分数
			mark.setMark(afteradd);// 修改分数	
			
			session.save(mark);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean addMark(Mark mark, Integer score, Timestamp sign_time) throws Exception {
		// TODO Auto-generated method stub		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			if(score >= 0) {
				Integer beforeadd = mark.getMark();// 原来的分数
				Integer afteradd = beforeadd + score;// 现在的分数
				Timestamp currenttime = TimeUtils.getCurrentDay();// 今天的日期
				
				mark.setMark(afteradd);// 修改分数		
				mark.setSign_time(currenttime);// 修改签到时间
				session.save(mark);
				return true;
			}
			else {
				return false;
			}
		} catch(Exception e) {
			
			return false;
		}
	}
	
	@Override
	public boolean dropMark(Mark mark,Integer score) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		if(score >= 0) {
			Integer beforeadd = mark.getMark();// 原来的分数
			Integer afteradd = beforeadd - score;// 现在的分数
			mark.setMark(afteradd);// 修改分数	
			session.save(mark);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Mark getMarkById(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		Session session= sessionFactory.getCurrentSession();
		
		String hql = "FROM Mark AS m WHERE m.user.userId = :userId";
		Query<?> query = session.createQuery(hql);
		query.setParameter("userId", userId);
		
		try {
			if(query.list().size() == 0)
				return null;
			else
				return (Mark)query.list().get(0);
		} catch(Exception e) {
			throw new Exception(e);
		}
	}


	

}
