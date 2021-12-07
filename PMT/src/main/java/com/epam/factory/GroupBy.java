package com.epam.factory;

import java.util.List;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

public class GroupBy extends Master implements Factory {

	Loggers LOGGER;

	public GroupBy() {
		LOGGER = Loggers.getLogger();
	}

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.viewGroupBy);
		Response response;
		UserActivities userActivities = new UserActivityDao();
		FilterCredentials filter = getFilterCredentials();
		response = userActivities.viewAccountFilter(filter);

		if (response.getStatus()) {
			List<AccountDetail> accountDetails = (List<AccountDetail>) response.getMsg();
			accountDetails.forEach(account -> LOGGER.printInfo(GroupBy.class, account.toString()));
		}

		return response.getStatus();
	}

}
