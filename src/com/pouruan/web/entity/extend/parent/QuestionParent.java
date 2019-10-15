package com.pouruan.web.entity.extend.parent;

import java.sql.Timestamp;
import java.util.Set;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Answer;

public class QuestionParent {
	private Integer id;
	private User user;
	private String title;
	private String discribe;
	private Integer mark;
	private Integer answer_token;
	private Timestamp create_time;
	private Set<Answer> answers;
	
	public Set<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	public void setAnswer_token(Integer answer_token) {
		this.answer_token = answer_token;
	}
	public Integer getAnswer_token() {
		return answer_token;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscribe() {
		return discribe;
	}
	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
