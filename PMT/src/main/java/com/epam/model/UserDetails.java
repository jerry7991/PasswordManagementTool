package com.epam.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.epam.singleton.EncryptDecrypt;
import com.epam.singleton.Loggers;

@Entity
@Table(name = "USER_DETAILS")
@NamedQuery(name = "GetUser", query = "SELECT e FROM UserDetails e  WHERE e.userName = :userName AND e.masterPassword = :password")
public class UserDetails {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name = "USER_NAME")
	private String userName;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = GroupDetails.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_FK")
	private List<GroupDetails> groupDetails;

	@Column(name = "PASSWORD")
	private String masterPassword;

	@Transient
	private Loggers LOGGER;

	@Transient
	private EncryptDecrypt encryptDecrypt;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<GroupDetails> getGroupDetails() {
		return groupDetails;
	}

	public void setGroupDetails(List<GroupDetails> groupDetails) {
		groupDetails.forEach(group -> {

		});
		this.groupDetails = groupDetails;
	}

	public UserDetails() {
		encryptDecrypt = EncryptDecrypt.getInstance();
		LOGGER = Loggers.getLogger();
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

	@Override
	public String toString() {
		StringBuilder userData = new StringBuilder();

		userData.append("Master Data \n");
		userData.append("User Name :: " + this.getUserName());
		userData.append("\n User Master Password :: " + this.getMasterPassword());
		userData.append("\n Data With their groups :: " + ((this.groupDetails != null) ? this.groupDetails : " "));
		return userData.toString();
	}
}
