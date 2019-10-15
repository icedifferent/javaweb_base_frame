package com.pouruan.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.IpLimitDao;
import com.pouruan.web.entity.IpLimit;
import com.pouruan.web.service.IpLimitService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class IpLimitServiceImpl implements IpLimitService{
	@Autowired
	private IpLimitDao ipLimitDao;
	@Override
	public int getTimesByIp(String ip,int type,boolean update) throws Exception {
		// TODO Auto-generated method stub
		List<IpLimit> list=ipLimitDao.getIpLimitByIp(ip,(byte)type);
		if(list.size()!=0){
			IpLimit ipLimit=list.get(0);
			if(!TimeUtils.isToday(ipLimit.getActionTime())){//如果这个ip最后的活动时间非今天，则重置数据
				ipLimit.setActionTime(TimeUtils.getCurrentDay());
				ipLimit.setTimes((byte)1);
				ipLimitDao.editIpLimit(ipLimit);//修改
				return 0;
			}else{
				if(update){
					ipLimit.setActionTime(TimeUtils.getCurrentDay());
					ipLimit.setTimes((byte)(ipLimit.getTimes()+1));
					ipLimitDao.editIpLimit(ipLimit);//修改
					return ipLimit.getTimes()-1;
				}else{
					return ipLimit.getTimes();
				}	
			}
		}else{//增加一条loginLog数据
			IpLimit loginLog=new IpLimit(TimeUtils.getCurrentDay(),
					ip,(byte)1,(byte)type);
			ipLimitDao.addIpLimit(loginLog);
		}
		return 0;
	}
	

}
