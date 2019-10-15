package com.pouruan.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.ConfigDao;
import com.pouruan.web.entity.Config;
import com.pouruan.web.service.ConfigService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//��������
@Service
public class ConfigServiceImpl implements ConfigService{
	@Autowired
	private ConfigDao configDao;
	@Override
	@Cacheable(value="caches")
	public Config getConfig()  throws Exception{//�����ļ�����ʹ�û���
		return configDao.getConfig();
	}
	@Override
	public boolean editConfig(Config config)  throws Exception{
		return configDao.editConfig(config);
	}
}
