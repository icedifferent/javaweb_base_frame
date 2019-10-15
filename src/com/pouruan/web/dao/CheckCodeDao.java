package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.CheckCode;

public interface CheckCodeDao {
	/**
	 * 增加验证码
	 * @param regsiterTemp
	 * @return boolean
	 */
	public boolean addCheckCode(CheckCode checkCode) throws Exception;
	

	
	/**
	 * 根据对象查找验证码
	 * @param checkCode
	 * @return List<RegsiterTemp>
	 */
	public   List<CheckCode>  getCheckCodeByObject(CheckCode checkCode) throws Exception;
	/**
	 * 修改注册记录
	 * @param regsiterTemp
	 * @return boolean
	 */
	public boolean editCheckCode(CheckCode checkCode) throws Exception;
}
