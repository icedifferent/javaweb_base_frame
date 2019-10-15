package com.pouruan.common.exception;

import com.pouruan.common.exception.AuthenticationException;

/**
 * ��֤��Ϣ�����쳣���磺�������
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class BadCredentialsException extends AuthenticationException {
	public BadCredentialsException() {
	}

	public BadCredentialsException(String msg) {
		super(msg);
	}
}
