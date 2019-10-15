package com.pouruan.common.encode;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ������ܽӿ�
 * 
 * @author liufang
 * 
 */
public interface PwdEncoder extends PasswordEncoder{
	/**
	 * �������
	 * 
	 * @param rawPass
	 *            δ�������룬null��Ϊ�մ�
	 * @return ���ܺ�����
	 */
	public String encodePassword(String rawPass);

	/**
	 * �������
	 * 
	 * @param rawPass
	 *            δ�������룬null��Ϊ�մ�
	 * @param salt
	 *            ������
	 * @return
	 */
	public String encodePassword(String rawPass, String salt);

	/**
	 * ��֤�����Ƿ���ȷ
	 * 
	 * @param encPass
	 *            ��������
	 * @param rawPass
	 *            δ�������룬null��Ϊ�մ�
	 * @return true:������ȷ��false:�������
	 */
	public boolean isPasswordValid(String encPass, String rawPass);

	/**
	 * ��֤�����Ƿ���ȷ
	 * 
	 * @param encPass
	 *            ��������
	 * @param rawPass
	 *            δ�������룬null��Ϊ�մ�
	 * @param salt
	 *            ������
	 * @return true:������ȷ��false:�������
	 */
	public boolean isPasswordValid(String encPass, String rawPass, String salt);
}
