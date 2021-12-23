package com.epam.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.epam.api.Validation;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.Response;

@Service
public class ValidationImpl implements Validation {

	@Override
	public Response isValidPassword(String password) {

		Response response;

		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(password);

		if (matcher.matches()) {
			response = new Response(true, "Matched");
		} else {
			response = new Response(false,
					"Password must be atleast length 8 and must contain numbers, alphabates and Uppercase");
		}

		return response;
	}

	@Override
	public Response isValidName(String name) {
		Response status = new Response(true, "Valid");
		status.setMsg("Valid name");
		for (int i = 0; i < name.length() && status.isStatus(); i++) {
			char ch = name.charAt(i);
			if (!Character.isAlphabetic(ch)) {
				status.setStatus(false);
				status.setMsg("Numerica or speacial character not allowed in name");
			}
		}
		return status;
	}

	@Override
	public Response isCorrectUrl(String url) {
		Response status = new Response(true, "Valid Url");
		try {
			new URL(url).toURI();
		} catch (URISyntaxException | MalformedURLException e) {
			status.setStatus(false);
			status.setMsg("InValid url");
		}
		return status;
	}

	@Override
	public Response isAccountValid(AccountDetailDto accountDetailDto) {
		Response response = isValidName(accountDetailDto.getAccountName());
		if (response.isStatus()) {
			response = isCorrectUrl(accountDetailDto.getUrl());
			if (response.isStatus()) {
				response = isValidPassword(accountDetailDto.getPassword());
			}
		}
		return response;
	}

}
