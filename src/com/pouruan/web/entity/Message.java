package com.pouruan.web.entity;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.parent.MessageParent;

public class Message extends MessageParent{
	public Message(){}
	public Message(String content,User fromUser, User toUser,int type){
		this.setContent(content);
		//this.setFromUserId(fromUserId);
		//this.setToUserId(toUserId);
		this.setToUser(toUser);
		this.setFromUser(fromUser);
		this.setIfRead((byte)0);
		this.setDate(TimeUtils.getCurrentDay());
		this.setType((byte)type);
	}
}
