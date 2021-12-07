package com.epam.factory;

import java.io.BufferedReader;
import java.io.IOException;

import com.epam.globaldata.Messages;
import com.epam.globaldata.Operations;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.GroupDetails;
import com.epam.model.Response;
import com.epam.singleton.Loggers;
import com.epam.singleton.Reader;

public class Master {

	private BufferedReader reader;
	private String typeOfAccount;
	private Loggers LOGGER;

	public Master() {
		reader = Reader.getReader();
		LOGGER = Loggers.getLogger();
	}

	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	public AccountDetail getAccountDetail() {
		Response status;
		AccountDetail accountDetail = new AccountDetail();
		try {
			if (typeOfAccount.equals(Operations.updateAccount)) {
				LOGGER.printInfo(Master.class, "Press enter for no change.");
			}

			LOGGER.printInfo(Master.class, "Enter Account name :: ");
			accountDetail.setAccountName(reader.readLine());

			String url = "";

			if (url.length() == 0 && typeOfAccount.equals(Operations.updateAccount)) {
				accountDetail.setUrl(url);
			} else {
				status = new Response(false, "");
				while (status.getStatus() == false) {
					// Because null is option for update to no change
					LOGGER.printInfo(Master.class, "Enter URL :: ");
					url = reader.readLine();
					status = accountDetail.isCorrectUrl(url);
				}
				accountDetail.setUrl(url);
			}

			LOGGER.printInfo(Master.class, "Enter Password :: ");
			String password = reader.readLine();

			if (password.length() == 0 && typeOfAccount.equals(Operations.updateAccount)) {
				accountDetail.setPassword(password);
			} else {
				status = accountDetail.isValidPassword(password);
				while (status.getStatus() == false) {
					LOGGER.printInfo(Master.class, (String) status.getMsg());
					password = reader.readLine();
					status = accountDetail.isValidPassword(password);
				}
				accountDetail.setPassword(password);
			}

		} catch (IOException ex) {
			LOGGER.printError(Master.class, ex.getMessage());
		}
		return accountDetail;
	}

	public GroupDetails getGroupDetails() {
		String groupName = "default";
		LOGGER.printInfo(Master.class, "Enter group name.");
		try {
			groupName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GroupDetails groupDetails = new GroupDetails();
		groupDetails.setGroupName(groupName);
		return groupDetails;
	}

	public FilterCredentials getFilterCredentials() {
		FilterCredentials filter = new FilterCredentials();
		LOGGER.printInfo(Master.class, "Provide Credentials. ");
		try {
			LOGGER.printInfo(Master.class, "Fill Filter data input. ");

			if (typeOfAccount.equals(Operations.updateGroupName)) {
				LOGGER.printInfo(Master.class, "Enter Older Group Name :: ");
			} else {
				LOGGER.printInfo(Master.class, "Enter Group Name :: ");
			}

			filter.setGroupName(reader.readLine());

			if (typeOfAccount.equals(Operations.updateAccount) || typeOfAccount.equals(Operations.deleteAccount)) {
				LOGGER.printInfo(Master.class, "Enter Account Name :: ");
				filter.setAccountName(reader.readLine());
			} else if (typeOfAccount.equals(Operations.viewGroupBy)) {
				LOGGER.printInfo(Master.class,
						"Enter Account Name :: (use * for all the accounts/ give name of Accounts) ");
				filter.setAccountName(reader.readLine());
			} else if (typeOfAccount.equals(Operations.all)) {
				filter.setAccountName("*");
			}
		} catch (IOException ex) {
			LOGGER.printError(Master.class, Messages.badInput);
		}
		return filter;

	}
}
