package com.pouruan.common.exception;

import com.pouruan.common.exception.AuthenticationException;

/**
 * �û���û���ҵ��쳣
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