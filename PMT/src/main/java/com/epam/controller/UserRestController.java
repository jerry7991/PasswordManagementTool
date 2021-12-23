package com.epam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.api.UserService;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.epam.exceptions.UserAlreadyExistException;

@RestController
@RequestMapping("/pmt")
public class UserRestController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/login", consumes = "application/json")
	public ResponseEntity<Object> login(@RequestBody UserData userData) {
		return new ResponseEntity<>(userService.login(userData).getMsg(), HttpStatus.OK);
	}

	@PostMapping("/addUser")
	public ResponseEntity<Object> addUser(@RequestBody UserData userData) throws UserAlreadyExistException {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserName(userData.getUserName());
		userDetails.setMasterPassword(userData.getPassword());
		boolean userAdded = userService.addUser(userDetails);
		return new ResponseEntity<>(userAdded, HttpStatus.OK);
	}
}
