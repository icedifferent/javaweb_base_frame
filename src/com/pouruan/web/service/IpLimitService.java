package com.pouruan.web.service;

public interface IpLimitService {
	/**
	 * 
	 * @param ip
	 * @param type  0Ϊ��¼��־  1Ϊע����־
	 * @param update  �Ƿ���Ҫ����
	 * @return  int��������
	 */
	public int getTimesByIp(String ip,int type,boolean update)  throws Exception;
	
}
