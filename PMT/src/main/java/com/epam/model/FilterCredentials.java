package com.epam.model;

import com.epam.factory.User;
import com.epam.singleton.EncryptDecrypt;
import com.epam.singleton.Loggers;

public class FilterCredentials {
	private String userName;
	private String masterPassword;
	private String groupName;
	private String accountName;
	private EncryptDecrypt encryptDecrypt;
	Loggers LOGGER;

	public FilterCredentials() {
		LOGGER = Loggers.getLogger();
		encryptDecrypt = EncryptDecrypt.getInstance();
		userName = User.userName;
		masterPassword = User.password;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMasterPassword() {
		return this.masterPassword;
//		return EncryptDecrypt.getDecrypted(this.masterPassword);
	}

	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
//		this.masterPassword = EncryptDecrypt.getEncrypted(masterPassword);
	}

}
