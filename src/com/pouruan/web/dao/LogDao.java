package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Log;

public interface LogDao {
	/**
	 * ������־
	 * @param log
	 * @return boolean
	 */
	public boolean wirteLog(Log log)throws Exception;
	
	/**
	 * �г�������������־
	 * @param pageNo
	 * @param pageSize
	 * @return  List<Log>
	 */
	public List<Log> listLogByCondition(Log log,int pageNo,int pageSize)throws Exception;
	
	/**
	 * �г�������������־����
	 *  @param log
	 * @return int
	 */
	public int  getLogCountByCondition(Log log)throws Exception;
}
