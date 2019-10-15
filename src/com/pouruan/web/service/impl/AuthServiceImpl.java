package com.pouruan.web.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.AuthDao;
import com.pouruan.web.entity.Authentication;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.AuthService;
import com.pouruan.web.service.ConfigService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class AuthServiceImpl implements AuthService{
	@Autowired
	private AuthDao authDao;
	@Autowired
	private ConfigService configService;
	@Override
	public User getUserByKey(String authKey) throws Exception {
		List<Authentication> list=authDao.getUserByKey(authKey);
		if(list.size()>1){//主键唯一，不会存在》1的情况
			
		}
		if(list.size()==1){
			Authentication auth =list.get(0);
			long millionSeconds =auth.getUpdateTime().getTime();
			if(millionSeconds+configService.getConfig().getAuthExpiryDate()*1000
					<System.currentTimeMillis()){//认证有效期过期了
				return null;
			}else{
				return auth.getUser();
			}
		}
		return null;
	}
	@Override
	public Authentication addAuthentication(int userId, String authKey,
			String ip) throws Exception {
		Authentication auth;
		List<Authentication> list=authDao.getUserByUserId(userId);
		if(list.size()==0){//认证已经存在了的话就直接修改就可以了
			auth =new Authentication(userId,authKey,ip,TimeUtils.getCurrentDay()
					,TimeUtils.getCurrentDay(),null);
			authDao.addAuthentication(auth);
		}else{
			auth =list.get(0);
			auth.setAuthenticationKey(authKey);
			auth.setLoginIp(ip);
			auth.setUpdateTime(TimeUtils.getCurrentDay());
			auth.setLoginTime(TimeUtils.getCurrentDay());
			authDao.editAuthentication(auth);
		}
		return auth;
	}
}
