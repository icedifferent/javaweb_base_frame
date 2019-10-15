package com.pouruan.web.dao;

import java.util.List;

import com.pouruan.web.entity.CheckCode;

public interface CheckCodeDao {
	/**
	 * ������֤��
	 * @param regsiterTemp
	 * @return boolean
	 */
	public boolean addCheckCode(CheckCode checkCode) throws Exception;
	

	
	/**
	 * ���ݶ��������֤��
	 * @param checkCode
	 * @return List<RegsiterTemp>
	 */
	public   List<CheckCode>  getCheckCodeByObject(CheckCode checkCode) throws Exception;
	/**
	 * �޸�ע���¼
	 * @param regsiterTemp
	 * @return boolean
	 */
	public boolean editCheckCode(CheckCode checkCode) throws Exception;
}
