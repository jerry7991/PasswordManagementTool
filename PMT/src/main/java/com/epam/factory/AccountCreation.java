package com.epam.factory;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.Response;
import com.epam.singleton.Loggers;
import com.epam.singleton.Reader;

@Component
public class AccountCreation extends Master implements Factory {
	@Autowired
	Loggers LOGGER;

	@Autowired
	@Qualifier(value = "userActivityDao")
	private UserActivityDao userActivities;

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.addAccount);
		Response response;
		BufferedReader reader = Reader.getReader();
		LOGGER.printInfo(AccountCreation.class, "Enter Group Name.");
		try {
			String groupName = reader.readLine();
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
