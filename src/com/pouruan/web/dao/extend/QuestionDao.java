package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;

public interface QuestionDao {
	/**
	 * ��������
	 * @param question
	 * @return boolean
	 */
	public boolean addQuestion(Question question) throws Exception ;
	
	/**
	 * ɾ������
	 * @param question
	 * @return boolean
	 */
	public Boolean deleteQuestion(Question question) throws Exception ;
	
	/**
	 * ����������ȡ����
	 * @param pageNo
	 * @param pageSize
	 * @return question
	 */
	public List<Question> listQuestionByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
	/**
	 * ����������ȡ��������
	 * @param question
	 * @return int
	 * @throws Exception
	 */
	public int getCountByCondition(Question question) throws Exception;
	
	/**
	 * ��������
	 * @param question
	 * @throws Exception
	 */
	public boolean updateQuestion(Question question) throws Exception;
}
