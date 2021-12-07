package com.epam.factory;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

public class DeleteAccount extends Master implements Factory {
	Loggers LOGGER;

	public DeleteAccount() {
		LOGGER = Loggers.getLogger();
	}

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.deleteAccount);
		Response response;
		UserActivities userActivities = new UserActivityDao();
		FilterCredentials filter = getFilterCredentials();
		response = userActivities.deletePasswordAccount(filter);

		LOGGER.printInfo(DeleteAccount.class, (String) response.getMsg());

		return response.getStatus();
	}
}
