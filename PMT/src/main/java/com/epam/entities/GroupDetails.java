package com.epam.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "GROUP_DETAILS")
public class GroupDetails {
	@Id
	@Column(name = "GROUP_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int groupId;

	@Column(name = "GROUP_NAME", unique = true)
	@Pattern(regexp = "(?=.*[A-Z])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper}!")
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
}
