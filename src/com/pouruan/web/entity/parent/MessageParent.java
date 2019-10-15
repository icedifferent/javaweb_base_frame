package com.pouruan.web.entity.parent;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringEscapeUtils;

import com.pouruan.web.entity.User;

public class MessageParent {
	private Integer messageId;
	private String content;
	//private Integer fromUserId;
	private User fromUser;
	//private Integer toUserId;
	private User toUser;
	private Timestamp date;
	private Byte ifRead;
	private Byte type;
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/*public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}*/
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public Byte getIfRead() {
		return ifRead;
	}
	public void setIfRead(Byte ifRead) {
		this.ifRead = ifRead;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
}
