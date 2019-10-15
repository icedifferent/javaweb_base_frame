package com.pouruan.web.entity.extend;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.parent.PostParent;

public class Post extends PostParent{
	public Post(){}
	public Post(String title ,String content,Module module,User user){
		this.setAddDate(TimeUtils.getCurrentDay());
		this.setContent(content);
		this.setLastRespondTime(TimeUtils.getCurrentDay());
		this.setModule(module);
		this.setReadTimes(0);
		this.setTitle(title);
		this.setUser(user);
	}
}
