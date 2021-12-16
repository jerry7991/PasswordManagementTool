package com.epam.exceptions;

public class ValidationFailedException extends Exception {
	public ValidationFailedException(String errMsg) {
		super(errMsg);
	}
}
