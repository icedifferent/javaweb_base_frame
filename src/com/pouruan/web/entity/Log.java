package com.pouruan.web.entity;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.parent.LogParent;

public class Log  extends LogParent{
	public Log(){}
	public Log (User user,String content,String ip,String url){
		this.setContent(content);
		this.setIp(ip);
		this.setTime(TimeUtils.getCurrentDay());
		this.setUrl(url);
		this.setUser(user);
	}
}
