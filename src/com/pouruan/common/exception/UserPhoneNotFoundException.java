package com.pouruan.common.exception;

import com.pouruan.common.exception.AuthenticationException;

/**
 * 用户名没有找到异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class UserPhoneNotFoundException extends AuthenticationException {
	public UserPhoneNotFoundException() {
	}

	public UserPhoneNotFoundException(String msg) {
		super(msg);
	}
}