package com.pouruan.web.entity.parent;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.pouruan.web.entity.Role;
import com.pouruan.web.entity.User;

public class AdminParent {
	//private Integer userId;
	private byte isSuper;
	private Timestamp addDate;
	private Set<Role> roles;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/*public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}*/
	public byte getIsSuper() {
		return isSuper;
	}
	public void setIsSuper(byte isSuper) {
		this.isSuper = isSuper;
	}
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
