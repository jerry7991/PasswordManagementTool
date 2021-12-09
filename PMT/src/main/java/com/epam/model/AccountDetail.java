package com.epam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.service.ValidationImpl;
import com.epam.singleton.Loggers;

@Entity
@Table(name = "ACCOUNT_DETAILS")
public class AccountDetail extends ValidationImpl {

	@Id
	@Column(name = "ACCOUNT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Transient
	private String groupName;

	private String url;
	private String password;

	@ManyToOne
	@JoinColumn(name = "GROUP_FK")
	private GroupDetails groupDetails;

	@Transient
	@Autowired
	private Loggers LOGGER;

	public GroupDetails getGroupDetails() {
		return groupDetails;
	}

	public void setGroupDetails(GroupDetails groupDetails) {
		this.groupDetails = groupDetails;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
//		return this.getDecryptedPassword(password);
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Account Name :" + this.accountName + ", ");
		str.append("Url :" + this.url + ", ");
		str.append("Password :" + password + "\n");
		return str.toString();
	}

}
