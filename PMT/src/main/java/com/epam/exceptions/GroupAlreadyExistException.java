package com.epam.exceptions;

public class GroupAlreadyExistException extends Exception {
	public GroupAlreadyExistException(String errMsg) {
		super(errMsg);
	}
}
