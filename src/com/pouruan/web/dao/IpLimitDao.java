package com.pouruan.web.dao;


import java.util.List;

import com.pouruan.web.entity.IpLimit;

public interface IpLimitDao {
	/**
	 * ����ip������־
	 * @param ip
	 * @param type
	 * @return List
	 */
	public List<IpLimit> getIpLimitByIp(String ip,byte type)throws Exception ;
	
	
	/**
	 * ����һ����־
	 * @param loginLog
	 * @return boolean
	 */
	public boolean addIpLimit(IpLimit ipLimit)throws Exception ;
	
	
	/**
	 * �޸���־
	 * @param loginLog
	 * @return boolean
	 */
	public boolean editIpLimit(IpLimit ipLimit)throws Exception ;
}
