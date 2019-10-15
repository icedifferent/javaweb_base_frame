package com.pouruan.web.service;
import java.util.List;

import com.pouruan.web.entity.Log;
import com.pouruan.web.entity.User;
public interface LogService {
	/**
	 * 记录日志
	 * @param content
	 * @param url
	 * @return
	 */
	public boolean wirteLog(String url,String content) throws Exception;
	
	/**
	 * 列出符合条件的日志
	 * @param pageNo
	 * @param pageSize
	 * @return  List<Log>
	 */
	public List<Log> listLogByCondition(Log log,int pageNo,int pageSize)  throws Exception;
	
	/**
	 * 根据条件列出日志总数
	 * @param log
	 * @return int
	 */
	public int  getLogCountByCondition(Log log) throws Exception;
}
