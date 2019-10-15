package com.pouruan.web.entity.extend;

import java.sql.Timestamp;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.parent.AnswerParent;
import com.pouruan.web.entity.extend.parent.QuestionParent;

public class Question extends QuestionParent{
	public Question(){}
	public Question(User user, String title, String discribe, Integer mark, Timestamp create_time){
		this.setUser(user);
		this.setTitle(title);
		this.setDiscribe(discribe);
		this.setMark(mark);
		this.setCreate_time(TimeUtils.getCurrentDay());
	}
}
