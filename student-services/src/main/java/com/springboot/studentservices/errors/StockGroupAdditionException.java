package com.springboot.studentservices.errors;

import org.springframework.dao.DataIntegrityViolationException;

public class StockGroupAdditionException extends DataIntegrityViolationException {

	private static final long serialVersionUID = 1L;

	public StockGroupAdditionException(String msg) {
		super(msg);
	}

}
