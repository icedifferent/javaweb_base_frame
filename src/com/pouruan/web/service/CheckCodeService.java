package com.pouruan.web.service;

import javax.servlet.http.HttpServletRequest;

import com.pouruan.web.entity.CheckCode;
import com.pouruan.web.entity.Config;
import com.pouruan.web.entity.User;


public interface CheckCodeService {
	/**
	 * ����ע���¼
	 * @param regsiterTemp
	 * @return boolean
	 */
	public boolean addCheckCode(CheckCode checkCode)  throws Exception;
	
	/**
	 * ��֤ע���¼
	 * @param phone
	 * @param code
	 * @return boolean
	 */
	public boolean toCheckCode(String phone ,String code) throws Exception;
	
	
	/**
	 * �����ʼ���֤��
	 * @param email
	 * @param code
	 * @return boolean
	 */
	public boolean toSendEmailCode(String email, String code)  throws Exception;
	
	
	/**
	 * �����ʼ� 
	 * @param config
	 * @param content
	 * @return boolean
	 */
	public boolean sendEmail(Config config,String content,String email)  throws Exception;
	
	

	/**
	 *  �����ʼ���������
	 * @param user
	 * @param email
	 * @param request
	 * @return boolean
	 */
	public boolean sendActEmail(User user,String email,HttpServletRequest request)  throws Exception;
}
