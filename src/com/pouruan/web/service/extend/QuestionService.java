package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;

public interface QuestionService {
	/**
	 * ��������
	 * @param question
	 * @return boolean
	 */
	public boolean addQuestion(String title, String discribe, Integer mark) throws Exception ;
	
	/**
	 * ɾ������
	 * @param question
	 * @return boolean
	 */
	public boolean deleteQuestion(Question question) throws Exception ;
	
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
	 * ����id��������
	 * @param id
	 * @return
	 */
	public Question getQuestionById(int id) throws Exception;
	
	/**
	 * ��ȡһ������
	 * @param question
	 * @return Question
	 * @throws Exception
	 */
	public Question getQuestion(Question question) throws Exception;
	
	/**
	 * ��������
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public boolean updateQuestion(Question question) throws Exception;
}
