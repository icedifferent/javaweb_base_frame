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
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//��������
@Service
public class AuthServiceImpl implements AuthService{
	@Autowired
	private AuthDao authDao;
	@Autowired
	private ConfigService configService;
	@Override
	public User getUserByKey(String authKey) throws Exception {
		List<Authentication> list=authDao.getUserByKey(authKey);
		if(list.size()>1){//����Ψһ��������ڡ�1�����
			
		}
		if(list.size()==1){
			Authentication auth =list.get(0);
			long millionSeconds =auth.getUpdateTime().getTime();
			if(millionSeconds+configService.getConfig().getAuthExpiryDate()*1000
					<System.currentTimeMillis()){//��֤��Ч�ڹ�����
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
		if(list.size()==0){//��֤�Ѿ������˵Ļ���ֱ���޸ľͿ�����
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
