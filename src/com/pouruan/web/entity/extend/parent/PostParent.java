package com.pouruan.web.entity.extend.parent;

import java.sql.Timestamp;
import java.util.Set;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.entity.extend.Reply;

public class PostParent {
	private Integer id;
	private String title;
	private String content;
	private Integer readTimes;
	private Timestamp lastRespondTime;
	private Timestamp addDate;
	private Integer replyCount;
	private User user;
	private Module module;
	private Set<Reply> replys;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}
	public Timestamp getLastRespondTime() {
		return lastRespondTime;
	}
	public void setLastRespondTime(Timestamp lastRespondTime) {
		this.lastRespondTime = lastRespondTime;
	}
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Set<Reply> getReplys() {
		return replys;
	}
	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	
}
