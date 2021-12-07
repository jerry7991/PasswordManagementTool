package com.epam.exceptions;

public class AccountMappedException extends Exception {

	public AccountMappedException(String errMsg) {
		super(errMsg);
	}

	public AccountMappedException(String accountName, String groupName) {
		super("Account = " + accountName + " already mapped with group =" + groupName
				+ " \n please use different name or different group");
	}

	public AccountMappedException() {
	}
}