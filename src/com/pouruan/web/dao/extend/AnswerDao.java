package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Question;

public interface AnswerDao {
	/**
	 * ����ش�
	 * @param Answer
	 * @return boolean
	 */
	public boolean addAnswer(Answer answer) throws Exception ;
	
	/**
	 * ɾ���ش�
	 * @param Answer
	 * @return boolean
	 */
	public Boolean deleteAnswer(Answer answer) throws Exception ;
	
	/**
	 * ���������ȡ�ش�
	 * @param question
	 * @param pageNo
	 * @param pageSize
	 * @return Answer
	 */
	public List<Answer> listAnswerByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
	/**
	 * ����������ȡ��������
	 * @param answer
	 * @return int
	 * @throws Exception
	 */
	public int getCountByCondition(Question question) throws Exception;
	
	/**
	 * ���������ȡ���ɻش�
	 * @param question
	 * @param pageNo
	 * @param pageSize
	 * @return Answer
	 */
	public List<Answer> listTakeAnswerByCondition(Question question, int pageNo,int pageSize) throws Exception ;
	
}
