package com.pouruan.web.dao;

import com.pouruan.web.entity.Config;

public interface ConfigDao {
	/**
	 * 加载网站的配置文件
	 * @return Config
	 */
	public Config getConfig()throws Exception ;
	
	
	/**
	 * 修改配置文件
	 * @param config
	 * @return boolean
	 */
	public boolean  editConfig(Config config) throws Exception ;
}
