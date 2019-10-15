package com.pouruan.web.service.extend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.extend.AnswerDao;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Question;
import com.pouruan.web.service.extend.AnswerService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class AnswerServiceImpl implements AnswerService{
	@Autowired
	private AnswerDao answerDao;
	@Override
	public boolean addAnswer(User user,Question question,String discribe,Integer is_token) throws Exception {
		question.setCreate_time(TimeUtils.getCurrentDay());
		Answer answer=new Answer(user,question,discribe,is_token);
		System.out.println("AnswerService1");
		List<Answer> list=answerDao.listAnswerByCondition(question, 1, 1);
		System.out.println("AnswerService2");
		if(list.size()!=0){
			if(System.currentTimeMillis()-list.get(0).getCreate_time().getTime()<1000*10){
				return false;//发帖时间较短
			}
		}
		return answerDao.addAnswer(answer);
	}
    //*可能有错*//
	@Override
	public boolean deleteAnswer(Answer answer) throws Exception {
	    Question question=new Question();
	    question.setId(answer.getQuestion().getId());
	    List<Answer> answerList=answerDao.listAnswerByCondition(question, 1, 1);
	    if(answerList.size()!=0){
	    	answer=answerList.get(0);
	    	return answerDao.deleteAnswer(answer);  	
	    }else{
	        return false;
	    }
	}

	@Override
	public List<Answer> listAnswerByCondition(Question question, int pageNo,
			int pageSize) throws Exception {
		List<Answer> answerList=answerDao.listAnswerByCondition(question, pageNo, pageSize);
		return answerList;
	}

	@Override
	public int getCountByCondition(Question question) throws Exception {
		return answerDao.getCountByCondition(question);
	}
	@Override
	public List<Answer> listTakeAnswerByCondition(Question question, int pageNo, int pageSize) throws Exception {
		List<Answer> answerList=answerDao.listTakeAnswerByCondition(question, pageNo, pageSize);
		return answerList;
	}

}
