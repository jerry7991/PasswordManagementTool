package com.epam.api;

import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.epam.exceptions.UserAlreadyExistException;

public interface UserService {
	public boolean addUser(UserDetails user) throws UserAlreadyExistException;

	public boolean userExist(UserData userData);

	public UserDetails getUserDetails(UserData userData);

	public Response login(UserData userData);

}