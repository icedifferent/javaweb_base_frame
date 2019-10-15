package com.pouruan.web.entity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.pouruan.web.entity.parent.UserParent;
/*
 * 可以通过视图实现一个实体类的数据来自多个表
 */
public class User extends UserParent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  Timestamp lastReplyPostTime;
	private  Timestamp lastAddPostTime;
	private  Timestamp lastSendMessageTime;
	public Timestamp getLastReplyPostTime() {
		return lastReplyPostTime;
	}
	public void setLastReplyPostTime(Timestamp lastReplyPostTime) {
		this.lastReplyPostTime = lastReplyPostTime;
	}
	public Timestamp getLastAddPostTime() {
		return lastAddPostTime;
	}
	public void setLastAddPostTime(Timestamp lastAddPostTime) {
		this.lastAddPostTime = lastAddPostTime;
	}
	public Timestamp getLastSendMessageTime() {
		return lastSendMessageTime;
	}
	public void setLastSendMessageTime(Timestamp lastSendMessageTime) {
		this.lastSendMessageTime = lastSendMessageTime;
	}
	public User(){};
	public User (String userName,String phone,String email,String userPwd
			,String portrait_img,byte status ,String register_ip,
			String character, Timestamp register_date,Byte isCheckEmail,String emailActCode){
		this.setCharacter(character);
		this.setEmail(email);
		this.setPhone(phone);
		this.setPortrait_img(portrait_img);
		this.setRegister_date(register_date);
		this.setRegister_ip(register_ip);
		this.setStatus(status);
		this.setUserName(userName);
		this.setUserPwd(userPwd);
		this.setIsCheckEmail(isCheckEmail);
		this.setEmailActCode(emailActCode);
		/**
		 * extend
		 */
		this.setLastAddPostTime(register_date);
		this.setLastReplyPostTime(register_date);
		this.setLastSendMessageTime(register_date);
	}
}
