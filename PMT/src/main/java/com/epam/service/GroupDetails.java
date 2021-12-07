package com.epam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.exceptions.AccountMappedException;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.globaldata.Messages;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.singleton.Loggers;

public class GroupDetails {
	private Loggers LOGGER;
	// Map<GroupName, Map<AccountName, AccountDetail>>
	private Map<String, Map<String, AccountDetail>> groupDetails;

	public GroupDetails() {
		groupDetails = new HashMap<>();
		LOGGER = Loggers.getLogger();
	}

	public Map<String, Map<String, AccountDetail>> getGroupData() {
		return this.groupDetails;
	}

	public Response isAccountMapped(String groupName, String accountName) {
		Response response = isGroupExist(groupName);
		if (response.getStatus()) {
			if (groupDetails.get(groupName).containsKey(accountName)) {
				response = new Response(true, Messages.sameAccountExist);
			} else {
				response = new Response(false, Messages.accNotFound);
			}
		}
		return response;

	}

	public Response isGroupExist(String groupName) {
		Response response = new Response();
		response.setStatus(groupDetails.containsKey(groupName));
		if (response.getStatus()) {
			response.setMsg(Messages.groupFound);
		} else {
			response.setMsg(Messages.groupNotFound);
		}
		return response;
	}

	public Response filterAccounts(FilterCredentials filterCredentials) {
		Response response = new Response();

		if (groupDetails.containsKey(filterCredentials.getGroupName())) {
			LOGGER.printInfo(GroupDetails.class, "Group Found");

			// Account Name -> Account Details
			Map<String, AccountDetail> accounts = groupDetails.get(filterCredentials.getGroupName());
			List<AccountDetail> accountDetails;
			if (filterCredentials.getAccountName().equals("*")) {
				// If user wants all the data
				accountDetails = new ArrayList<>(accounts.values());
			} else {
				// For particular user
				accountDetails = new ArrayList<>();
				accountDetails.add(accounts.get(filterCredentials.getAccountName()));
			}
			response.setMsg(accountDetails);
		} else {
			response.setStatus(false);
			response.setMsg(Messages.groupNotFound);
		}
		return response;
	}

	public Response addGroupDetails(String groupName, AccountDetail accountDetails) {
		Response response = new Response();
		try {

			// Check whether group exist or not.
			if (groupDetails.containsKey(groupName)) {
				Map<String, AccountDetail> accounts = groupDetails.get(groupName);

				if (accounts.containsKey(accountDetails.getAccountName())) {
					throw new AccountMappedException(accountDetails.getAccountName(), groupName);
				}
				accounts.put(groupName, accountDetails);
			} else {
				// Let's move ahead with another group
				Map<String, AccountDetail> newAccounts = new HashMap<>();
				newAccounts.put(accountDetails.getAccountName(), accountDetails);
				groupDetails.put(groupName, newAccounts);
			}
			response.setMsg(accountDetails);
		} catch (AccountMappedException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
			LOGGER.printError(GroupDetails.class, ex.getMessage());
		}

		return response;
	}

	public Response modifyPasswordAccount(FilterCredentials filter, AccountDetail newDetails) {
		Response response = new Response();
		try {
			String groupName = filter.getGroupName();
			String accountName = filter.getAccountName();
			if (!groupDetails.containsKey(groupName)) {
				throw new GroupNotFoundException(groupName + Messages.groupNotFound);
			}

			Map<String, AccountDetail> accountDetails = groupDetails.get(groupName);

			if (!accountDetails.containsKey(accountName) || (newDetails.getAccountName().length() != 0
					&& accountDetails.containsKey(newDetails.getAccountName()))) {
				throw new AccountMappedException(accountName, groupName);
			}

			AccountDetail prevDetails = accountDetails.get(accountName);

			if (newDetails.getAccountName().length() != 0) {
				prevDetails.setAccountName(newDetails.getAccountName());
			}

			if (newDetails.getGroupName().length() != 0) {
				prevDetails.setGroupName(newDetails.getGroupName());
			}

			if (newDetails.getUrl().length() != 0) {
				prevDetails.setUrl(newDetails.getUrl());
			}

			if (newDetails.getPassword().length() != 0) {
				prevDetails.setPassword(newDetails.getPassword());
			}

			if (newDetails.getAccountName().length() > 0 && accountName.equals(newDetails.getAccountName()) == false) {
				prevDetails.setAccountName(newDetails.getAccountName());
			}

			if (!prevDetails.getAccountName().equals(newDetails.getAccountName())) {
				accountDetails.remove(accountName);
				accountDetails.put(newDetails.getAccountName(), prevDetails);
			}

			response.setMsg(prevDetails);

		} catch (GroupNotFoundException | AccountMappedException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
			LOGGER.printError(GroupDetails.class, ex.getMessage());
		}
		return response;
	}

	public Response modifyGroupName(FilterCredentials filter, String newGroupName) {
		Response response = new Response();
		try {
			String olderGroupName = filter.getGroupName();
			if (olderGroupName.equalsIgnoreCase("default") || newGroupName.equalsIgnoreCase("default")
					|| olderGroupName.equalsIgnoreCase(newGroupName)) {
				throw new UnsupportedOperationException(Messages.defaultOverriden);
			}

			// if selected group not present
			if (groupDetails.containsKey(olderGroupName) == false) {
				throw new GroupNotFoundException(olderGroupName + Messages.groupNotFound);
			}

			// If new group already exist
			if (groupDetails.containsKey(newGroupName)) {
				throw new GroupAlreadyExistException(Messages.groupOverriden);
			}

			// Make a clone, Delete older once and add new
			Map<String, AccountDetail> accounts = new HashMap<>(groupDetails.get(olderGroupName));

			// Change the data within all the accounts
			accounts.entrySet().stream().forEach(entry -> {
				AccountDetail account = entry.getValue();
				account.setGroupName(newGroupName);
			});

			groupDetails.remove(olderGroupName);
			groupDetails.put(newGroupName, accounts);
			// No conflict because we are handling here.
			response.setMsg(Messages.doneUpdate);

		} catch (GroupNotFoundException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		} catch (GroupAlreadyExistException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
			LOGGER.printError(GroupDetails.class, ex.getMessage());
		} catch (UnsupportedOperationException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
			LOGGER.printError(GroupDetails.class, ex.getMessage());
		}
		return response;
	}

	public Response deleteGroup(String groupName) {
		Response response = new Response();
		try {
			if (!groupDetails.containsKey(groupName)) {
				throw new GroupNotFoundException(groupName + Messages.groupNotFound);
			}
			groupDetails.remove(groupName);
			response.setMsg("Group name = " + groupName + Messages.deleted);
		} catch (GroupNotFoundException ex) {
			response.setStatus(false);
			response.setMsg(ex.getMessage());
			LOGGER.printError(GroupDetails.class, ex.getMessage());
		}
		return response;
	}

	public Response deletePasswordAccount(String groupName, String accountName) {
		Response response = this.isGroupExist(groupName);
		if (response.getStatus()) {
			if (groupDetails.get(groupName).containsKey(accountName)) {
				response = new Response(true, Messages.deleted);
			} else {
				response.setStatus(false);
				response.setMsg(Messages.accNotFound);
			}
		}
		return response;
	}

	@Override
	public String toString() {
		StringBuilder groupData = new StringBuilder();

		groupDetails.entrySet().stream().forEach(entry -> {
			groupData.append("\n Group = " + entry.getKey() + "\n");
			entry.getValue().entrySet().stream().forEach(accountsEntry -> {
				groupData.append(accountsEntry.getValue().toString() + "\n");
			});
		});
		return groupData.toString();
	}
}
