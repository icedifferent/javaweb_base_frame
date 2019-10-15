package com.pouruan.web.entity.extend.parent;

import java.sql.Timestamp;

import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Post;

public class ReplyParent {
	private Integer id;
	private String content;
	private User replyUser;
	private Post post;
	private Timestamp date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public void gDate(Timestamp date) {
		this.date = date;
	}
}
