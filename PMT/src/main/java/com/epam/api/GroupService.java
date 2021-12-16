package com.epam.api;

import java.util.List;

import com.epam.dto.Response;
import com.epam.entities.GroupDetails;

public interface GroupService {
	public boolean addGroup(String groupName);

	public boolean deleteGroup(int groupId, String groupName);

	public List<GroupDetails> getAllGroup();

	public Response modifyGroupName(int groupId, String newGroupName);

}
