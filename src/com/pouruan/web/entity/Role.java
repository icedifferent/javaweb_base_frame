package com.pouruan.web.entity;

import java.io.Serializable;

import com.pouruan.web.entity.parent.RoleParent;

public class Role extends RoleParent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Role(){}

	public Role(Admin admin,Permission permission){
		this.setAdmin(admin);
		this.setPermission(permission);
	}
}
