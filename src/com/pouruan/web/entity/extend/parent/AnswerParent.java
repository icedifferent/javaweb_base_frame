package com.pouruan.web.entity.extend.parent;

import java.sql.Timestamp;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Question;

public class AnswerParent {
	private Integer id;
	private User user;
	private Question question;
	private String discribe;
	private Integer is_token;
	private Timestamp create_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public QuestionParent getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getDiscribe() {
		return discribe;
	}
	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}
	public Integer getIs_token() {
		return is_token;
	}
	public void setIs_token(Integer is_token) {
		this.is_token = is_token;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
}