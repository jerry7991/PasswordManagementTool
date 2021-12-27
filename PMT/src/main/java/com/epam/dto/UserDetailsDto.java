package com.epam.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDto {

	private int userId;

	private String userName;

	private List<GroupDetailsDto> groupDetailsDto;

	private String token;

	private String masterPassword;
}
