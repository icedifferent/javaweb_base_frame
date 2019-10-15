package com.pouruan.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.pouruan.web.entity.parent.IpLimitParent;

public class IpLimit extends IpLimitParent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IpLimit(){};
	public IpLimit(Timestamp actionTime,String ip,byte times,byte type){
		this.setActionTime(actionTime);
		this.setIp(ip);
		this.setTimes(times);
		this.setType(type);
	}
}
