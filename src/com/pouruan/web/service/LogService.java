package com.pouruan.web.service;
import java.util.List;

import com.pouruan.web.entity.Log;
import com.pouruan.web.entity.User;
public interface LogService {
	/**
	 * ��¼��־
	 * @param content
	 * @param url
	 * @return
	 */
	public boolean wirteLog(String url,String content) throws Exception;
	
	/**
	 * �г�������������־
	 * @param pageNo
	 * @param pageSize
	 * @return  List<Log>
	 */
	public List<Log> listLogByCondition(Log log,int pageNo,int pageSize)  throws Exception;
	
	/**
	 * ���������г���־����
	 * @param log
	 * @return int
	 */
	public int  getLogCountByCondition(Log log) throws Exception;
}
