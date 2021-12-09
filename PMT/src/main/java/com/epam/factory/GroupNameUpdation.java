package com.epam.factory;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.dao.UserActivityDao;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;
import com.epam.singleton.Reader;

@Component
public class GroupNameUpdation implements Factory {

	@Autowired
	Loggers LOGGER;

	@Override
	public boolean execute() {

		UserActivities userActivities = new UserActivityDao();
		FilterCredentials filter = new FilterCredentials();
		BufferedReader reader = Reader.getReader();
		Response response;

		LOGGER.printInfo(GroupNameUpdation.class, "Enter Current group name.");

		try {
			filter.setGroupName(reader.readLine());
			response = userActivities.modifyGroupName(filter, reader.readLine());
			LOGGER.printInfo(GroupDeletion.class, (String) response.getMsg());
		} catch (IOException e) {
			LOGGER.printInfo(GroupDeletion.class, "Bad Input");
			response = new Response(false, "Bad Input");
		}
		return response.getStatus();
	}

}
