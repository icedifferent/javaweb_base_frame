package com.pouruan.common.exception;

import com.pouruan.common.exception.AuthenticationException;

/**
 * 用户名没有找到异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class UsernameNotFoundException extends AuthenticationException {
	public UsernameNotFoundException() {
	}

	public UsernameNotFoundException(String msg) {
		super(msg);
	}
}