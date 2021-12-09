package com.epam.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.api.Validation;
import com.epam.globaldata.Messages;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

@Component
public class ValidationImpl implements Validation {

	@Autowired
	Loggers LOGGER;

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
	public Response isGroupNameValid(String name) {
		Response status = new Response();
		status.setMsg(Messages.validAcc);
		for (int i = 0; i < name.length() && status.getStatus(); i++) {
			char ch = name.charAt(i);
			if (!Character.isAlphabetic(ch)) {
				status.setStatus(false);
				status.setMsg(Messages.numaricFound + ", " + Messages.specialCharFound);
			}
		}
		return status;
	}

	@Override
	public Response isCorrectUrl(String url) {
		Response status = new Response();
		try {
			new URL(url).toURI();

			status.setMsg(Messages.validUrl);
		} catch (URISyntaxException e) {
			status.setStatus(false);
			status.setMsg(Messages.inValidUrl);
			LOGGER.printError(ValidationImpl.class, Messages.inValidUrl);
		} catch (MalformedURLException e) {
			status.setStatus(false);
			status.setMsg(Messages.inValidUrl);
			LOGGER.printError(ValidationImpl.class, Messages.inValidUrl);
		}
		return status;
	}

}
