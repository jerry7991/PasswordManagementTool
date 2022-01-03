package com.epam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.api.UserService;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.epam.exceptions.UserAlreadyExistException;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ModelAndView login(UserData userData) {
		Response response = userService.login(userData);
		ModelAndView modelView = new ModelAndView();
		if (response != null && response.isStatus()) {
			modelView.setViewName("viewGroupBy");
			UserDetails userDetails = (UserDetails) response.getMsg();
			modelView.addObject("GroupDetails", userDetails.getGroupDetails());
		} else {
			modelView.setViewName("error");
			modelView.addObject("response", response);
		}
		return modelView;
	}

	@PostMapping("/creatUser")
	public ModelAndView addUser(UserData userData) throws UserAlreadyExistException {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserName(userData.getUserName());
		userDetails.setMasterPassword(userData.getPassword());
		boolean userAdded = userService.addUser(userDetails);
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("home");
		modelView.addObject("message", userAdded);
		return modelView;
	}

	@RequestMapping("/home")
	public String home() {
		return "home";
	}

	@RequestMapping("/addUser")
	public String addNewUser() {
		return "AddUser";
	}
}