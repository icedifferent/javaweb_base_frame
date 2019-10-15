package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;

public interface QuestionService {
	/**
	 * 发表问题
	 * @param question
	 * @return boolean
	 */
	public boolean addQuestion(String title, String discribe, Integer mark) throws Exception ;
	
	/**
	 * 删除问题
	 * @param question
	 * @return boolean
	 */
	public boolean deleteQuestion(Question question) throws Exception ;
	
	/**
	 * 根据条件获取问题
	 * @param pageNo
	 * @param pageSize
	 * @return question
	 */
	public List<Question> listQuestionByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
	/**
	 * 根据条件获取问题数量
	 * @param question
	 * @return int
	 * @throws Exception
	 */
	public int getCountByCondition(Question question) throws Exception;
	
	/**
	 * 根据id查找问题
	 * @param id
	 * @return
	 */
	public Question getQuestionById(int id) throws Exception;
	
	/**
	 * 获取一个问题
	 * @param question
	 * @return Question
	 * @throws Exception
	 */
	public Question getQuestion(Question question) throws Exception;
	
	/**
	 * 更新问题
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public boolean updateQuestion(Question question) throws Exception;
}
