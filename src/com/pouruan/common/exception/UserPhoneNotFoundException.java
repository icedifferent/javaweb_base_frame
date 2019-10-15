package com.pouruan.common.exception;

import com.pouruan.common.exception.AuthenticationException;

/**
 * �û���û���ҵ��쳣
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