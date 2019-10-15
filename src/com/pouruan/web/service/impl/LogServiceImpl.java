package com.pouruan.web.service.impl;

import java.util.List;






import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.LogDao;
import com.pouruan.web.entity.Log;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.LogService;
import com.pouruan.web.service.security.CommonService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class LogServiceImpl implements LogService{
	@Autowired 
	private CommonService commonService;
	@Autowired
	private LogDao logDao;
	@Override
	public boolean wirteLog(String url, String content) throws Exception {
		User user =commonService.getCustomUser();
		String ip=commonService.getAuthenticationDetails().getRemoteAddress();
		Log log= new Log(user,content,ip,url);
		return logDao.wirteLog(log);
	}

	@Override
	public List<Log> listLogByCondition(Log log,int pageNo,int pageSize) throws Exception{
		return  logDao.listLogByCondition(log,pageNo,pageSize);
	}

	@Override
	public int getLogCountByCondition(Log log) throws Exception {
		return  logDao.getLogCountByCondition(log);
	}

}
