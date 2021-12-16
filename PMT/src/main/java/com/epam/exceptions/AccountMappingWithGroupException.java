package com.epam.exceptions;

public class AccountMappingWithGroupException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountMappingWithGroupException(String errMsg) {
		super(errMsg);
	}
}
