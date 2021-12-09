package com.epam.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.exceptions.CommandNotRecognizedException;
import com.epam.globaldata.Operations;
import com.epam.model.Response;

@Component
public class Factories implements ApplicationContextAware {

	private Map<String, Factory> factories;
	private ApplicationContext applicationContext;

	@Autowired
	private UserActivities userActivities;

	private final void init() {
		factories = new HashMap<>();
		factories.put(Operations.addUser, applicationContext.getBean(UserCreation.class));
		factories.put(Operations.addAccount, applicationContext.getBean(AccountCreation.class));
		factories.put(Operations.viewGroupBy, applicationContext.getBean(GroupBy.class));
		factories.put(Operations.updateAccount, applicationContext.getBean(AccountUpdation.class));
		factories.put(Operations.updateGroupName, applicationContext.getBean(GroupNameUpdation.class));
		factories.put(Operations.deleteGroup, applicationContext.getBean(GroupDeletion.class));
		factories.put(Operations.deleteAccount, applicationContext.getBean(DeleteAccount.class));

		// For debug only. No need to give credentials
		factories.put(Operations.all, applicationContext.getBean(AllDetails.class));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		this.init();
	}

	public Response getFactory(String choice) {
		Response response = new Response();
		try {
			Factory factory = factories.getOrDefault(choice, null);
			if (factory == null) {
				throw new CommandNotRecognizedException("No such choice exist");
			}
			response.setMsg(factory);
		} catch (CommandNotRecognizedException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
		}
		return response;
	}

	public void close() {
		userActivities.close();
	}

}
