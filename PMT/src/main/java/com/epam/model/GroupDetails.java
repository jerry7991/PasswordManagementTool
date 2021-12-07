package com.epam.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP_DETAILS")
@NamedQueries({ @NamedQuery(name = "FetchGroup", query = "select e from GroupDetails e where e.groupName= :groupName"),
		@NamedQuery(name = "GetGroupId", query = "select e.groupId from GroupDetails e where e.groupName= :groupName") })
public class GroupDetails {
	@Id
	@Column(name = "GROUP_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int groupId;

	@Column(name = "GROUP_NAME", unique = true)
	private String groupName;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = AccountDetail.class, fetch = FetchType.LAZY, mappedBy = "groupDetails")
	private List<AccountDetail> accounts;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<AccountDetail> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDetail> accounts) {
		accounts.forEach(account -> account.setGroupDetails(this));
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Group Id : " + groupId + ", Group Name : " + groupName + ", Accounts :\n");
		accounts.forEach(account -> {
			string.append(account.toString());
		});
		return string.toString();
	}
}
