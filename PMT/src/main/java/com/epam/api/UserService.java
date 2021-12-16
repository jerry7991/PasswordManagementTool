package com.epam.api;

import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;

public interface UserService {
	public Response addUser(UserDetails user);

	public boolean userExist(UserData userData);

	public UserDetails getUserDetails(UserData userData);

	public Response login(UserData userData);

}