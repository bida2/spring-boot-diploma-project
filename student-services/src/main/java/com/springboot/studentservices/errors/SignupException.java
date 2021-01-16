package com.springboot.studentservices.errors;

import org.springframework.dao.DataIntegrityViolationException;

public class SignupException extends DataIntegrityViolationException {

	private static final long serialVersionUID = 1L;

	public SignupException(String msg) {
		super(msg);
	}

}
