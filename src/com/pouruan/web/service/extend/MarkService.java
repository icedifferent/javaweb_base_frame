package com.pouruan.web.service.extend;

import java.sql.Timestamp;

import com.pouruan.web.entity.extend.Mark;

public interface MarkService {
	
	/**
	 * ���ӷ��������ǲ���ǩ�����ڽ����޸�
	 * @param mark {id,userId,mark,sign_time}
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
	
	/**
	 * �����û�id���ж��û��Ƿ��Ѿ��ڽ�����й�ǩ���
	 * @param userId
	 * @return 1:  �û��Ѿ����й�ǩ����  0: �û���û����ǩ�� 
	 * @throws Exception
	 */
	public int isSign(Integer userId) throws Exception ;
	
}
