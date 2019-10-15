package com.pouruan.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.parent.AdminParent;

public class Admin extends AdminParent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Admin(){}
	public Admin(User user,byte isSuper){
		this.setUser(user);
		this.setIsSuper(isSuper);
		this.setAddDate(TimeUtils.getCurrentDay());
	}
}
