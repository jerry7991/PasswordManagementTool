package com.epam.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupDetailsDto {
	private int userId;

	private int groupId;

	private String groupName;

	private List<AccountDetailDto> accountsDto;
}
