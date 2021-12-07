package com.epam.PMT;

import com.epam.model.GroupDetails;
import com.epam.model.UserDetails;

public class UserDetailsTest {
	public static UserDetails gUserDetails() {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(1);
		userDetails.setUserName("anup");
		userDetails.setMasterPassword("Anup@1234");

		GroupDetails groupDetails = new GroupDetails();
		groupDetails.setGroupId(2);
		groupDetails.setGroupName("google");

//		AccountDe

		return userDetails;
	}
}
