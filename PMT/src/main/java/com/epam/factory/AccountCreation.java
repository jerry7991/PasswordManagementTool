package com.epam.factory;

import java.io.BufferedReader;
import java.io.IOException;

import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.Response;
import com.epam.singleton.Loggers;
import com.epam.singleton.Reader;

public class AccountCreation extends Master implements Factory {
	Loggers LOGGER;

	public AccountCreation() {
		LOGGER = Loggers.getLogger();
	}

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.addAccount);
		Response response;
		BufferedReader reader = Reader.getReader();
		LOGGER.printInfo(AccountCreation.class, "Enter Group Name.");
		UserActivityDao userActivities;
		try {
			String groupName = reader.readLine();
			userActivities = new UserActivityDao();
			AccountDetail account = getAccountDetail();
			account.setGroupName(groupName);
			response = userActivities.createPasswordAccount(account);
			LOGGER.printInfo(AccountCreation.class, (String) response.getMsg());

		} catch (IOException e) {
			LOGGER.printError(AccountCreation.class, "Bad Input");
			response = new Response(false, "Bad Input");
		}
		return response.getStatus();
	}

}
