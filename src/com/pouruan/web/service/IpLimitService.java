package com.pouruan.web.service;

public interface IpLimitService {
	/**
	 * 
	 * @param ip
	 * @param type  0为登录日志  1为注册日志
	 * @param update  是否需要更新
	 * @return  int操作次数
	 */
	public int getTimesByIp(String ip,int type,boolean update)  throws Exception;
	
}
