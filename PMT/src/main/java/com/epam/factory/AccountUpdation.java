package com.epam.factory;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

public class AccountUpdation extends Master implements Factory {

	Loggers LOGGER;

	public AccountUpdation() {
		LOGGER = Loggers.getLogger();
	}

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.updateAccount);
		Response response;
		UserActivities userActivities = new UserActivityDao();
		FilterCredentials filter = getFilterCredentials();
		AccountDetail accountDetail = getAccountDetail();
		response = userActivities.modifyPasswordAccount(filter, accountDetail);
		LOGGER.printInfo(AccountUpdation.class, (String) response.getMsg());
		return response.getStatus();
	}

}
