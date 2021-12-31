package com.epam.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.util.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = AccountMappingWithGroupException.class)
	public Map<String, String> accountMappingWithGroupException(
			AccountMappingWithGroupException accountMappingWithGroupException) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SERVICE, "Account Mapping");
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, accountMappingWithGroupException.getMessage());
		response.put(Constants.STATUS, HttpStatus.CONFLICT.name());
		return response;
	}

	@ExceptionHandler(value = AccountNotFoundException.class)
	public Map<String, String> accountNotFoundException(AccountNotFoundException accountNotFoundException) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SERVICE, "Account Not Found");
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, accountNotFoundException.getMessage());
		response.put(Constants.STATUS, HttpStatus.NOT_FOUND.name());
		return response;
	}

	@ExceptionHandler(value = AuthenticationFailedExceptions.class)
	public ResponseEntity<Map<String, String>> authenticationFailedExceptions(
			AuthenticationFailedExceptions authenticationFailedExceptions) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SERVICE, "User Login");
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, authenticationFailedExceptions.getMessage());
		response.put(Constants.STATUS, HttpStatus.UNAUTHORIZED.name());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = GroupAlreadyExistException.class)
	public ResponseEntity<Map<String, String>> groupAlreadyExistException(
			GroupAlreadyExistException groupAlreadyExistException) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SERVICE, "GroupServiceImpl");
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, groupAlreadyExistException.getMessage());
		response.put(Constants.STATUS, HttpStatus.CONFLICT.name());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = GroupNotFoundException.class)
	public ResponseEntity<Map<String, String>> groupNotFoundException(GroupNotFoundException groupNotFoundException) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SERVICE, "Group");
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, groupNotFoundException.getMessage());
		response.put(Constants.STATUS, HttpStatus.NOT_FOUND.name());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = UserAlreadyExistException.class)
	public ResponseEntity<Map<String, String>> userAlreadyExistException(
			UserAlreadyExistException userAlreadyExistException) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SERVICE, "addUser");
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, userAlreadyExistException.getMessage());
		response.put(Constants.STATUS, HttpStatus.CONFLICT.name());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

}
