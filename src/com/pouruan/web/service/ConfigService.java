package com.pouruan.web.service;

import com.pouruan.web.entity.Config;

public interface ConfigService {
	/**
	 * ������վ�������ļ�
	 * @return Config
	 */
	public Config getConfig() throws Exception;
	
	
	
	/**
	 * �޸������ļ�
	 * @param config
	 * @return boolean
	 */
	public boolean  editConfig(Config config)  throws Exception;
}
