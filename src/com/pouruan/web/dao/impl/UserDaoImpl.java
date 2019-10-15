package com.pouruan.web.dao.impl;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pouruan.web.dao.UserDao;
import com.pouruan.web.entity.Config;
import com.pouruan.web.entity.User;

@Repository
public class  UserDaoImpl implements UserDao{
	@Autowired
	private  SessionFactory sessionFactory;
	private Logger logger = LogManager.getRootLogger();
	


	
	


	@Override
	public boolean addUser(User user) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.save(user);
		}catch(Exception e){
			logger.error("Ôö¼ÓuserÊ§°Ü"+e);
			throw new Exception(e);
		}
		return true;
	}

	
	
	
	
	@Override
	public boolean editUser(User user) throws Exception {
		Session session= sessionFactory.getCurrentSession();
		try{
			session.update(user);
		}catch(Exception e){
			logger.error("¸üÐÂuserÊ§°Ü"+e);
			throw new Exception(e);
		}
		session.flush();
		return true;
	}

	

	
	
	@Override
	public List<User> getUserByCondition(User user, int pageNo,int pageSize) throws Exception {
		String condition ="1=1 ";
		if(user.getUserId()!=null){
			condition+=" And u.userId =:userId";
		}
		if(user.getUserName()!=null){
			condition+=" And u.userName  like :userName";
		}
		if(user.getPhone()!=null){
			condition+=" And u.phone  =:phone";
		}
		if(user.getEmail()!=null){
			condition+=" And u.email  = :email";
		}
		if(user.getStatus()!=null){
			condition+=" And u.status  =:status";
		}
		condition+=" order by u.userId desc" ;
		String hql = "FROM User AS u WHERE "+condition;
		Session session= sessionFactory.getCurrentSession();
		Query<?> query =session.createQuery(hql);
		if(user.getUserId()!=null)
			query.setParameter("userId", user.getUserId());
		if(user.getPhone()!=null)
			query.setParameter("phone",user.getPhone());
		if(user.getUserName()!=null)
			query.setParameter("userName","%"+user.getUserName()+"%");
		if(user.getEmail()!=null)
			query.setParameter("email",user.getEmail());
		if(user.getStatus()!=null)
			query.setParameter("status",user.getStatus());
		try {
			return (List<User>) query.setFirstResult((pageNo-1)*pageSize)  
	        .setMaxResults(pageSize)  
	        .list();
		}catch(Exception e){
			throw new Exception(e);
		} 
	}






	@Override
	public int getUserCountByCondition(User user) throws Exception {
		String condition ="1=1 ";
		if(user.getUserId()!=null){
			condition+=" And u.userId =:userId";
		}
		if(user.getUserName()!=null){
			condition+=" And u.userName  like :userName";
		}
		if(user.getPhone()!=null){
			condition+=" And u.phone  =:phone";
		}
		if(user.getEmail()!=null){
			condition+=" And u.email   = :email";
		}
		if(user.getStatus()!=null){
			condition+=" And u.status  =:status";
		}
		String hql = "select count(*) FROM User AS u WHERE "+condition;
		Session session= sessionFactory.getCurrentSession();
		Query<?> query =session.createQuery(hql);
		if(user.getUserId()!=null)
			query.setParameter("userId", user.getUserId());
		if(user.getPhone()!=null)
			query.setParameter("phone",user.getPhone());
		if(user.getUserName()!=null)
			query.setParameter("userName","%"+user.getUserName()+"%");
		if(user.getEmail()!=null)
			query.setParameter("email",user.getEmail());
		if(user.getStatus()!=null)
			query.setParameter("status",user.getStatus());
		try {
			return ((Number)query.uniqueResult()).intValue(); 
		}catch(Exception e){
			throw new Exception(e);
		} 
	}
	
	
	
	
	//@Transactional()  
	public List<User> showInfo(){
		Session session= sessionFactory.getCurrentSession();
		/*User user =new User();
		user.setCharacter("À²À²");
		user.setEmail("123456@qq.com");
		user.setPhone("123456789");
		user.setPortrait_img("http://123");
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String str=sp.format(now);
		user.setRegister_date(Timestamp.valueOf(str));
		user.setRegister_ip("172.28.89.9");
		user.setStatus((byte)1);
		user.setUserName("hello");
		user.setUserPwd("394960830");
		session.save(user);
		List list=new ArrayList();
		System.out.println(list.get(10));*/
		//User u=session.load(User. class, 1);User u2=session.load(User. class, 1);
		//System.out.println(u.getEmail());System.out.println(u2.getEmail());
		DetachedCriteria dc = DetachedCriteria. forClass (User. class);
		Criteria c = dc.getExecutableCriteria(session);
		c.setCacheable(true);//¿ªÆô»º´æ
		List<?> list = c.list();
		Criteria b = dc.getExecutableCriteria(session);
		c.setCacheable(true);//¿ªÆô»º´æ
		list = b.list();
		if(list.size()!=0)
			return (List<User>) list;
		else
			return null;
	}



































































}
