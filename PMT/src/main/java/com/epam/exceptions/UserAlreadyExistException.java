package com.epam.exceptions;

public class UserAlreadyExistException extends Exception {
	public UserAlreadyExistException(String errMsg) {
		super(errMsg);
	}
}
