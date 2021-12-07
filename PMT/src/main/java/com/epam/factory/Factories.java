package com.epam.factory;

import java.util.HashMap;
import java.util.Map;

import com.epam.exceptions.CommandNotRecognizedException;
import com.epam.globaldata.Operations;
import com.epam.model.Response;

public class Factories {

	private Map<String, Factory> factories;

	public Factories() {
		factories = new HashMap<>();
		factories.put(Operations.addUser, new UserCreation());
		factories.put(Operations.addAccount, new AccountCreation());
		factories.put(Operations.viewGroupBy, new GroupBy());
		factories.put(Operations.updateAccount, new AccountUpdation());
		factories.put(Operations.updateGroupName, new GroupNameUpdation());
		factories.put(Operations.deleteGroup, new GroupDeletion());
		factories.put(Operations.deleteAccount, new DeleteAccount());

		// For debug only. No need to give credentials
		factories.put(Operations.all, new AllDetails());
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
}
