package com.pouruan.web.service.extend.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.extend.MarkDao;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Mark;
import com.pouruan.web.service.extend.MarkService;
import com.pouruan.web.service.security.CommonService;

@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	private MarkDao markDao;
	@Autowired 
	private CommonService commonService;
	
	@Override
	public boolean addMark(Mark mark, Integer score) throws Exception {
		// TODO Auto-generated method stub
		return markDao.addMark(mark, score);
		
	}

	@Override
	public boolean addMark(Mark mark, Integer score, Timestamp sign_time) throws Exception {
		// TODO Auto-generated method stub	
		return markDao.addMark(mark, score, sign_time);
	}

	@Override
	public boolean dropMark(Mark mark, Integer score) throws Exception {
		// TODO Auto-generated method stub
		
		return markDao.dropMark(mark, score);
	}

	@Override
	public Mark getMarkById(Integer userId) throws Exception {
		// TODO Auto-generated method stub

		return markDao.getMarkById(userId);
	}
	
	public int isSign(Integer userId) throws Exception {
		
		Mark mark = markDao.getMarkById(userId);
		if(mark == null) return 0;
		if(TimeUtils.isToday(mark.getSign_time()))
			return 1;
		return 0;
	}

}
