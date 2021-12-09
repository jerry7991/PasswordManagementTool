package com.epam.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

@Component
public class DeleteAccount extends Master implements Factory {
	@Autowired
	Loggers LOGGER;

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
