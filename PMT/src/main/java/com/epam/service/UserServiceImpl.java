package com.epam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.api.UserService;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.epam.exceptions.AuthenticationFailedExceptions;
import com.epam.exceptions.UserAlreadyExistException;
import com.epam.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserData userData;

	@Override
	public boolean addUser(UserDetails userDetails) throws UserAlreadyExistException {
		if (userRepository.existsByUserName(userDetails.getUserName())) {
			throw new UserAlreadyExistException("User Already exist.");
		}
		userRepository.save(userDetails);
		return userRepository.existsByUserNameAndMasterPassword(userDetails.getUserName(),
				userDetails.getMasterPassword());

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
			logger.error(ex.getMessage());
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
			logger.error(ex.getMessage());
		}
		return response;
	}
}
