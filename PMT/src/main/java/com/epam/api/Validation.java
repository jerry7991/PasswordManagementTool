package com.epam.api;

import com.epam.model.Response;

public interface Validation {
	public Response isValidPassword(String password);

	public Response isGroupNameValid(String accountName);

	public Response isCorrectUrl(String url);
}