package com.pouruan.web.entity.parent;

import java.sql.Timestamp;
import java.util.Date;

public class IpLimitParent {
	private Integer id;
	private String  ip;
	private Timestamp actionTime;
	private Byte times;
	private Byte type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getActionTime() {
		return actionTime;
	}
	public void setActionTime(Timestamp actionTime) {
		this.actionTime = actionTime;
	}
	public Byte getTimes() {
		return times;
	}
	public void setTimes(Byte times) {
		this.times = times;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
}
