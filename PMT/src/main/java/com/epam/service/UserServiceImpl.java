package com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.api.UserService;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.epam.exceptions.AuthenticationFailedExceptions;
import com.epam.exceptions.UserAlreadyExistException;
import com.epam.repositories.UserRepository;
import com.epam.util.Loggers;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Loggers logger;

	@Autowired
	private UserData userData;

	@Override
	public Response addUser(UserDetails userDetails) {
		Response response = null;
		try {
			if (userRepository.findByUserName(userDetails.getUserName()) != null) {
				throw new UserAlreadyExistException("User Already exist.");
			}
			userRepository.save(userDetails);
			boolean isAdded = userRepository.existsByUserNameAndMasterPassword(userDetails.getUserName(),
					userDetails.getMasterPassword());
			response = new Response(isAdded, isAdded ? "User Added SuccessFully" : "User Addition Failed");
		} catch (UserAlreadyExistException ex) {
			logger.printError(UserServiceImpl.class, ex.getMessage());
			response = new Response(false, ex.getMessage());
		}
		return response;
	}

	@Override
	public boolean userExist(UserData userData) {
		return userRepository.existsByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword());
	}

	@Override
	public UserDetails getUserDetails(UserData userData) {
		UserDetails userDetails = null;
		try {
			userDetails = userRepository.findByUserNameAndMasterPassword(userData.getUserName(),
					userData.getPassword());
			if (userDetails == null) {
				throw new AuthenticationFailedExceptions("User Id or Password are incorrect");
			}
		} catch (AuthenticationFailedExceptions ex) {
			logger.printError(UserServiceImpl.class, ex.getMessage());
		}
		return userDetails;
	}

	@Override
	public Response login(UserData userData) {
		Response response;
		try {
			if (!userExist(userData)) {
				throw new AuthenticationFailedExceptions("User Id or password are incorrect");
			}
			UserDetails userDetails = getUserDetails(userData);
			if (userDetails == null)
				throw new AuthenticationFailedExceptions("User Id or password are incorrect");
			this.userData.setId(userDetails.getUserId());
			this.userData.setPassword(userDetails.getMasterPassword());
			this.userData.setUserName(userDetails.getMasterPassword());
			response = new Response(true, userDetails);
		} catch (AuthenticationFailedExceptions ex) {
			response = new Response(false, ex.getMessage());
			logger.printFatal(UserServiceImpl.class, ex.getMessage());
		}
		return response;
	}
}
