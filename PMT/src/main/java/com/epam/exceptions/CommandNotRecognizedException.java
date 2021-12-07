package com.epam.exceptions;

public class CommandNotRecognizedException extends Exception {
	public CommandNotRecognizedException(String err) {
		super(err);
	}

	public CommandNotRecognizedException() {

	}
}
