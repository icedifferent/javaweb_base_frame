package com.pouruan.web.entity;

import java.io.Serializable;

import com.pouruan.web.entity.parent.ConfigParent;



public class Config extends ConfigParent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Config(){}

	/*private int hashCode = Integer.MIN_VALUE;
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Admin)) return false;
		else {
			Config c = (Config) obj;
			if (null == this.getId() || null == c.getId()) return false;
			else return (this.getId().equals(c.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}*/
}
