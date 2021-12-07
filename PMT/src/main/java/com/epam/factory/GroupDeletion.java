package com.epam.factory;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

public class GroupDeletion extends Master implements Factory {
	Loggers LOGGER;

	public GroupDeletion() {
		LOGGER = Loggers.getLogger();
	}

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.viewGroupBy);
		Response response;
		UserActivities userActivities = new UserActivityDao();
		FilterCredentials filter = getFilterCredentials();
		response = userActivities.deleteGroup(filter);
		LOGGER.printInfo(GroupDeletion.class, (String) response.getMsg());
		return response.getStatus();
	}
}
