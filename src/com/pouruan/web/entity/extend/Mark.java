package com.pouruan.web.entity.extend;

import java.sql.Timestamp;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.parent.MarkParent;

public class Mark extends MarkParent{

	public Mark() {}
	public Mark(User user,Integer mark,Timestamp sign_time) {
		
		this.setUser(user);
		this.setMark(mark);
		this.setSign_time(TimeUtils.getCurrentDay());
	}
}
