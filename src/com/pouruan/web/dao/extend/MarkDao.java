package com.pouruan.web.dao.extend;

import java.sql.Timestamp;

import com.pouruan.web.entity.extend.Mark;

public interface MarkDao {

	/**
	 * ���ӷ��������ǲ���ǩ�����ڽ����޸�
	 * @param mark {id,userid,mark,sign_time}
	 * @param score ����������
	 * @return
	 * @throws Exception
	 */
	public boolean addMark(Mark mark,Integer score) throws Exception ;
	
	/**
	 * ���ӷ��������Ҷ�ǩ�����ڽ����޸ģ�����˵�����ǩ���޸ķ�����ר����ڣ�
	 * @param mark
	 * @param score ����������
	 * @param sign_time ���ǩ������
	 * @return
	 * @throws Exception
	 */
	public boolean addMark(Mark mark,Integer score,Timestamp sign_time) throws Exception ;
	
	
	/**
	 * ���ٷ��������ǲ���ǩ�����ڽ����޸�
	 * @param mark 
	 * @param score ����������
	 * @return
	 * @throws Exception
	 */
	public boolean dropMark(Mark mark,Integer score) throws Exception ;
	
	
	/**
	 * ����������ȡ�����������������������ע�⣬�˴�����ȥ�Ĳ������û���id������Ӧ������취��ȡ�û���idȻ���ٵ���
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	public Mark getMarkById(Integer userId) throws Exception ;
	
	
	
}
