package com.epam.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

@Component
public class GroupBy extends Master implements Factory {

	@Autowired
	Loggers LOGGER;
	@Autowired
	UserActivities userActivities;

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.viewGroupBy);
		Response response;
		FilterCredentials filter = getFilterCredentials();
		response = userActivities.viewAccountFilter(filter);

		if (response.getStatus()) {
			List<AccountDetail> accountDetails = (List<AccountDetail>) response.getMsg();
			accountDetails.forEach(account -> LOGGER.printInfo(GroupBy.class, account.toString()));
		}

		return response.getStatus();
	}

}
