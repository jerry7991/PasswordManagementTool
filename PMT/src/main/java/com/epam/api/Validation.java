package com.epam.api;

import com.epam.dto.AccountDetailDto;
import com.epam.dto.Response;

public interface Validation {
	public Response isValidPassword(String password);

	public Response isValidName(String name);

	public Response isCorrectUrl(String url);

	public Response isAccountValid(AccountDetailDto accountDetailDto);
}