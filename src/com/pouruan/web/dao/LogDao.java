package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.Log;

public interface LogDao {
	/**
	 * 保存日志
	 * @param log
	 * @return boolean
	 */
	public boolean wirteLog(Log log)throws Exception;
	
	/**
	 * 列出符合条件的日志
	 * @param pageNo
	 * @param pageSize
	 * @return  List<Log>
	 */
	public List<Log> listLogByCondition(Log log,int pageNo,int pageSize)throws Exception;
	
	/**
	 * 列出符合条件的日志总数
	 *  @param log
	 * @return int
	 */
	public int  getLogCountByCondition(Log log)throws Exception;
}
