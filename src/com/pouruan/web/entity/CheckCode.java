package com.pouruan.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.pouruan.web.entity.parent.CheckCodeParent;

public class CheckCode extends CheckCodeParent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckCode(){}
	public CheckCode(String checkVar,String checkCode,Timestamp codeDate,byte status){
		this.setCheckCode(checkCode);
		this.setCodeDate(codeDate);
		this.setCheckVar(checkVar);
		this.setStatus(status);
	}
}
