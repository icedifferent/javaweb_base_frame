package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;

public interface QuestionDao {
	/**
	 * 发表问题
	 * @param question
	 * @return boolean
	 */
	public boolean addQuestion(Question question) throws Exception ;
	
	/**
	 * 删除问题
	 * @param question
	 * @return boolean
	 */
	public Boolean deleteQuestion(Question question) throws Exception ;
	
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
	 * 更新问题
	 * @param question
	 * @throws Exception
	 */
	public boolean updateQuestion(Question question) throws Exception;
}
