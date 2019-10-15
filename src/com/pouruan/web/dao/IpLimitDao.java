package com.pouruan.web.dao;


import java.util.List;

import com.pouruan.web.entity.IpLimit;

public interface IpLimitDao {
	/**
	 * 根据ip查找日志
	 * @param ip
	 * @param type
	 * @return List
	 */
	public List<IpLimit> getIpLimitByIp(String ip,byte type)throws Exception ;
	
	
	/**
	 * 增加一条日志
	 * @param loginLog
	 * @return boolean
	 */
	public boolean addIpLimit(IpLimit ipLimit)throws Exception ;
	
	
	/**
	 * 修改日志
	 * @param loginLog
	 * @return boolean
	 */
	public boolean editIpLimit(IpLimit ipLimit)throws Exception ;
}
