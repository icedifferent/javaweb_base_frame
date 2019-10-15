package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Question;

public interface AnswerDao {
	/**
	 * 发表回答
	 * @param Answer
	 * @return boolean
	 */
	public boolean addAnswer(Answer answer) throws Exception ;
	
	/**
	 * 删除回答
	 * @param Answer
	 * @return boolean
	 */
	public Boolean deleteAnswer(Answer answer) throws Exception ;
	
	/**
	 * 根据问题获取回答
	 * @param question
	 * @param pageNo
	 * @param pageSize
	 * @return Answer
	 */
	public List<Answer> listAnswerByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
	/**
	 * 根据条件获取问题数量
	 * @param answer
	 * @return int
	 * @throws Exception
	 */
	public int getCountByCondition(Question question) throws Exception;
	
	/**
	 * 根据问题获取采纳回答
	 * @param question
	 * @param pageNo
	 * @param pageSize
	 * @return Answer
	 */
	public List<Answer> listTakeAnswerByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
}
