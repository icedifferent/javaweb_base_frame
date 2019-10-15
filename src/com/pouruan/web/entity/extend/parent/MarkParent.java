package com.pouruan.web.entity.extend.parent;

import java.sql.Timestamp;

import com.pouruan.web.entity.User;

public class MarkParent {

	private Integer id;
	private User user;
	private Integer mark;
	private Timestamp sign_time;
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public Timestamp getSign_time() {
		return sign_time;
	}
	public void setSign_time(Timestamp sign_time) {
		this.sign_time = sign_time;
	}
	
	
}
