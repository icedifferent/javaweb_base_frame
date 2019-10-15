package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;
import com.pouruan.web.entity.extend.Reply;

public interface AnswerService {
	/**
	 * 发表回答
	 * @param user
	 * @param question
	 * @param discribe
	 * @param is_token
	 * @return boolean
	 */
	public boolean addAnswer(User user,Question question,String discribe,Integer is_token) throws Exception;
	
	/**
	 * 删除回复
	 * @param answerId
	 * @return boolean
	 */
	public boolean deleteAnswer(Answer answer) throws Exception;
	
	/**
	 * 根据问题获取回答
	 * @param question
	 * @param pageNo
	 * @param pageSize
	 * @return Answer
	 */
	public List<Answer> listAnswerByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
	/**
	 * 根据问题获取回答个数
	 * @param answer
	 * @return int
	 */
	public int getCountByCondition(Question question) throws Exception ;
	
	/**
	 * 根据问题获取采纳回答
	 * @param question
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Answer> listTakeAnswerByCondition(Question question, int pageNo,int pageSize) throws Exception ;
}
