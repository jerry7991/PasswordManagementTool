package com.epam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.api.GroupService;
import com.epam.api.Validation;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.exceptions.ValidationFailedException;
import com.epam.repositories.GroupRepository;
import com.epam.repositories.UserRepository;
import com.epam.util.Loggers;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserData userData;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private Loggers logger;

	@Autowired
	private Validation validation;

	@Override
	public boolean addGroup(String groupName) {
		Response response = validation.isValidName(groupName);
		try {
			if (!response.isStatus()) {
				throw new ValidationFailedException((String) response.getMsg());
			}
			if (groupRepository.existsByGroupNameAndUserId(userData.getId(), groupName) != null) {
				throw new GroupAlreadyExistException(groupName + " already mapped with the user");
			}

			UserDetails userDetails = userRepository.getById(userData.getId());
			GroupDetails groupDetails = new GroupDetails();
			groupDetails.setGroupName(groupName);
			userDetails.getGroupDetails().add(groupDetails);
			UserDetails addedUserDetails = userRepository.save(userDetails);
			response.setMsg("Group Added");
			response.setStatus(addedUserDetails != null);
			logger.printDebug(GroupServiceImpl.class, "User Details saved");
		} catch (ValidationFailedException | GroupAlreadyExistException ex) {
			logger.printError(GroupServiceImpl.class, ex.getMessage());
			response = new Response(false, ex.getMessage());
		}
		return response.isStatus();
	}

	@Override
	public boolean deleteGroup(int groupId, String groupName) {
		boolean isDeleted = false;
		try {
			if (groupRepository.existsByGroupNameAndUserId(userData.getId(), groupName) == null) {
				throw new GroupNotFoundException(groupName + " not mapped with the user");
			}

			groupRepository.deleteById(groupId);
			isDeleted = !groupRepository.existsById(groupId);

		} catch (GroupNotFoundException ex) {
			logger.printError(GroupServiceImpl.class, ex.getMessage());
		}
		return isDeleted;
	}

	@Override
	public List<GroupDetailsDto> getAllGroup() {

		return (groupRepository.findByUserId(userData.getId())).stream()
				.map(group -> modelMapper.map(group, GroupDetailsDto.class)).collect(Collectors.toList());

	}

	@Override
	public Response modifyGroupName(int groupId, String newGroupName) {
		Response response = null;
		try {
			if (groupRepository.existsByGroupNameAndUserId(userData.getId(), newGroupName) != null) {
				throw new GroupAlreadyExistException(newGroupName + " already mapped with the user");
			}

			GroupDetails groupDetails = groupRepository.getById(groupId);
			groupDetails.setGroupName(newGroupName);

			groupRepository.save(groupDetails);

			response = new Response(true, "Group Updated");

		} catch (GroupAlreadyExistException ex) {
			response = new Response(false, ex.getMessage());
			logger.printError(GroupServiceImpl.class, ex.getMessage());
		}
		return response;
	}

	@Override
	public GroupDetailsDto getGroupByName(String groupName) {
		GroupDetailsDto groupDetailsDto = null;
		try {
			if (groupRepository.existsByGroupNameAndUserId(userData.getId(), groupName) != null) {
				throw new GroupNotFoundException(groupName + " not mapped with the user");
			}
			groupDetailsDto = modelMapper.map(groupRepository.findByGroupNameAndUserId(userData.getId(), groupName),
					GroupDetailsDto.class);
		} catch (GroupNotFoundException ex) {
			logger.printError(GroupServiceImpl.class, ex.getMessage());
		}
		return groupDetailsDto;
	}
}
