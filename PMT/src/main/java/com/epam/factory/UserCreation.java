package com.epam.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.GroupDetails;
import com.epam.model.Response;
import com.epam.model.UserDetails;
import com.epam.singleton.Loggers;
import com.epam.singleton.Reader;

@Component
public class UserCreation extends Master implements Factory {
	@Autowired
	private Loggers LOGGER;

	@Override
	public boolean execute() {
		BufferedReader read = Reader.getReader();
		Response response = new Response();
		setTypeOfAccount(Operations.addUser);
		try {
			LOGGER.printInfo(UserCreation.class, "Enter User Name");
			User.userName = read.readLine();
			LOGGER.printInfo(UserCreation.class, "Enter Password");
			User.password = read.readLine();

			UserDetails userDetails = new UserDetails();
			userDetails.setUserName(User.userName);
			userDetails.setMasterPassword(User.password);

			List<GroupDetails> groupDetails = new ArrayList<>();

			GroupDetails group = getGroupDetails();
			AccountDetail account = getAccountDetail();
			account.setGroupDetails(group);
			group.setAccounts(Arrays.asList(account));
			groupDetails.add(group);

			userDetails.setGroupDetails(groupDetails);

			response = new UserActivityDao().addUser(userDetails);
			LOGGER.printInfo(UserCreation.class, (String) response.getMsg());
		} catch (IOException e) {
			LOGGER.printError(UserCreation.class, e.getMessage());
			response = new Response(false, e.getMessage());
		}
		return response.getStatus();
	}

}
