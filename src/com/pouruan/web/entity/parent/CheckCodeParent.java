package com.pouruan.web.entity.parent;

import java.sql.Timestamp;

public class CheckCodeParent {
	private Integer Id;
	private String checkVar;
	private String checkCode;
	private Timestamp codeDate;
	private Byte status;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getCheckVar() {
		return checkVar;
	}
	public void setCheckVar(String checkVar) {
		this.checkVar = checkVar;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public Timestamp getCodeDate() {
		return codeDate;
	}
	public void setCodeDate(Timestamp codeDate) {
		this.codeDate = codeDate;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
}
