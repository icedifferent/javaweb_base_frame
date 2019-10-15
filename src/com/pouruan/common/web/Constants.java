package com.pouruan.common.web;

/**
 * web常量
 * 
 * @author liufang
 * 
 */
public abstract class Constants {
	/**
	 * 路径分隔符
	 */
	public static final String SPT = "/";
	/**
	 * 索引页
	 */
	public static final String INDEX = "index";
	/**
	 * 默认模板
	 */
	public static final String DEFAULT = "default";
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * 提示信息
	 */
	public static final String MESSAGE = "message";
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * url中的jsessionid名称
	 */
	public static final String JSESSION_URL = "jsessionid";
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	/**
	 * HTTP GET请求
	 */
	public static final String GET = "GET";
	
	/**
	 * cookie中用户的身份认证
	 */
	public static final String AUTH_KEY = "auth_key";
	
	/**
	 * url中用户当前主的链接
	 */
	public static final String PROCESS_URL = "processUrl";
	
	/**
	 * url中用户需要返回页面的链接（/=index.do）
	 */
	public static final String RETURN_URL = "returnUrl";
}
