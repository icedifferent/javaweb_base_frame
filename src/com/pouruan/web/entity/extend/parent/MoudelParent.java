package com.pouruan.web.entity.extend.parent;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import com.pouruan.web.entity.extend.Post;
public class MoudelParent {
	private Integer id;
	private String name;
	private Set<Post> posts;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		name=StringEscapeUtils.escapeHtml4(name);
		this.name = name;
	}
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
}
