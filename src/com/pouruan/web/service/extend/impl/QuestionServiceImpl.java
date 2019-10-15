package com.pouruan.web.service.extend.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.extend.QuestionDao;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;
import com.pouruan.web.service.extend.QuestionService;
import com.pouruan.web.service.security.CommonService;

@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private QuestionDao questionDao;
	@Autowired 
	private CommonService commonService;
	
	@Override
	public boolean addQuestion(String title, String discribe, Integer mark) throws Exception {
		User user=commonService.getCustomUser();
		Question oldQuestion=new Question();
		oldQuestion.setUser(user);
		oldQuestion=this.getQuestion(oldQuestion);
		if(oldQuestion!=null){
			if(System.currentTimeMillis()-oldQuestion.getCreate_time().getTime()<1000*120){
				return false;//发表问题时间较短
			}
		}
		Timestamp create_time = TimeUtils.getCurrentDay();// 今天的日期
		Question question=new Question(user, title, discribe, mark, create_time);
		return questionDao.addQuestion(question);
	}
	
	@Override
	public boolean deleteQuestion(Question question) throws Exception {
		return questionDao.deleteQuestion(question);
	}
	
	@Override
	public List<Question> listQuestionByCondition(Question question, int pageNo, int pageSize) throws Exception {
		return questionDao.listQuestionByCondition(question, pageNo, pageSize);
	}
	
	@Override
	public int getCountByCondition(Question question) throws Exception {
		return questionDao.getCountByCondition(question);
	}
	
	@Override
	public Question getQuestionById(int id) throws Exception {
		Question question=new Question();
		question.setId(id);
		return this.getQuestion(question);
	}
	
	@Override
	public Question getQuestion(Question question) throws Exception {
		List<Question> list=listQuestionByCondition(question, 1,1);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean updateQuestion(Question question) throws Exception {
		return questionDao.updateQuestion(question);
	}

}
