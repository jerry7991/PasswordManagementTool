package com.epam.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.globaldata.Operations;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.model.UserDetails;
import com.epam.singleton.Loggers;

@Component
public class AllDetails extends Master implements Factory {

	@Autowired
	Loggers LOGGER;

	@Override
	public boolean execute() {
		setTypeOfAccount(Operations.all);
		Response response;
		UserActivities userActivities = new UserActivityDao();
		FilterCredentials filter = getFilterCredentials();
		response = userActivities.getAllDetails(filter);

		if (response.getStatus()) {
			List<UserDetails> userDetails = (List<UserDetails>) response.getMsg();
			userDetails.forEach(user -> LOGGER.printInfo(AccountUpdation.class, user.toString()));
		}

		return response.getStatus();
	}

}
