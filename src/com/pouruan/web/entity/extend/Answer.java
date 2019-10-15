package com.pouruan.web.entity.extend;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.parent.AnswerParent;



public class Answer extends AnswerParent{
	public Answer(){};
	public Answer(User user,Question question,String discribe,Integer is_token){
		this.setUser(user);
		this.setQuestion(question);
		this.setDiscribe(discribe);
		this.setIs_token(is_token);
		this.setCreate_time(TimeUtils.getCurrentDay());		
	}

}
