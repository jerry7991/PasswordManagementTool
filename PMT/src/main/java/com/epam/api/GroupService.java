package com.epam.api;

import java.util.List;

import com.epam.dto.GroupDetailsDto;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.exceptions.GroupNotFoundException;

public interface GroupService {
	public boolean addGroup(String groupName) throws GroupAlreadyExistException;

	public List<GroupDetailsDto> getAllGroup();

	public boolean modifyGroupName(int groupId, String newGroupName) throws GroupAlreadyExistException;

	public GroupDetailsDto getGroupByName(String groupName) throws GroupNotFoundException;

	boolean deleteGroup(int groupId) throws GroupNotFoundException;
}
