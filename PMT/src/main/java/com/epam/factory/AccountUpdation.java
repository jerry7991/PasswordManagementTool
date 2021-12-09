package com.epam.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

@Component
public class AccountUpdation extends Master implements Factory {

	@Autowired
	Loggers LOGGER;

	@Autowired
	@Qualifier(value = "userActivityDao")
	private UserActivities userActivities;

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.updateAccount);
		Response response;
		FilterCredentials filter = getFilterCredentials();
		AccountDetail accountDetail = getAccountDetail();
		response = userActivities.modifyPasswordAccount(filter, accountDetail);
		LOGGER.printInfo(AccountUpdation.class, (String) response.getMsg());
		return response.getStatus();
	}

}
