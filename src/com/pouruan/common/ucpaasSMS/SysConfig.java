package com.pouruan.common.ucpaasSMS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SysConfig {
	private Properties props = null;// config.properties
	private static Logger log = Logger.getLogger(SysConfig.class);
	private static volatile SysConfig conf;

	private SysConfig() {
		props = new Properties();
		loadConfigProps();
	}

	public static SysConfig getInstance() {
		if (conf == null) {
			synchronized (SysConfig.class) {
				if (conf == null) {
					conf = new SysConfig();
				}
			}
		}
		return conf;
	}

	public void loadConfigProps() {
		InputStream is = null;
		try {
			String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
	        path=path.replace('/', '\\'); // ��/����\  
	        path=path.replace("file:", ""); //ȥ��file:  
	        path=path.replace("classes\\", ""); //ȥ��class\  
	        path=path.replace("build\\", ""); //ȥ��class\  
	        path=path.substring(1); //ȥ����һ��\,�� \D:\JavaWeb...
	        String t;
	       try{
	    	   t="WebContent/WEB-INF/config/ucpaasSmsConfig.properties";  
	    	   is =new FileInputStream(path+t);
	       }catch(Exception e){
	    	   t="config/ucpaasSmsConfig.properties";  
	    	   is =new FileInputStream(path+t);
	       }
			InputStreamReader reader = new InputStreamReader(is, "UTF-8");
			props.load(reader);
			Iterator<String> iter = props.stringPropertyNames().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				props.setProperty(key, props.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("load config.properties error!please check the file!", e);
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getProperty(String key) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return tmp.trim();
		}
		return tmp;
	}

	public String getProperty(String key, String defaultValue) {
		String tmp = props.getProperty(key, defaultValue);
		if (StringUtils.isNotEmpty(tmp)) {
			return tmp.trim();
		}
		return tmp;
	}

	public int getPropertyInt(String key) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return Integer.parseInt(tmp.trim());
		}
		return 0;

	}

	public int getPropertyInt(String key, int defaultValue) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return Integer.parseInt(tmp.trim());
		}
		return defaultValue;
	}

	public long getPropertyLong(String key, long defaultValue) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return Integer.parseInt(tmp.trim());
		}
		return defaultValue;
	}
}