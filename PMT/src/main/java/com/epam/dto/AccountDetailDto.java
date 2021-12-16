package com.epam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDetailDto {

	private int accountId;

	private String accountName;

	private int groupId;

	private String url;

	private String password;

	private GroupDetailsDto groupDetailsDto;

}
