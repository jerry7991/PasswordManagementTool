package com.epam.api;

import java.util.List;

import com.epam.dto.GroupDetailsDto;
import com.epam.dto.Response;

public interface GroupService {
	public boolean addGroup(String groupName);

	public boolean deleteGroup(int groupId, String groupName);

	public List<GroupDetailsDto> getAllGroup();

	public Response modifyGroupName(int groupId, String newGroupName);

	public GroupDetailsDto getGroupByName(String groupName);
}
