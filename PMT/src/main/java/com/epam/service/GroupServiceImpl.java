package com.epam.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.api.GroupService;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.UserData;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.repositories.GroupRepository;
import com.epam.repositories.UserRepository;

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

	private final Logger logger = LogManager.getLogger(GroupServiceImpl.class);

	@Override
	public boolean addGroup(String groupName) throws GroupAlreadyExistException {
		if (groupRepository.existsByGroupNameAndUserId(userData.getId(), groupName)
				.compareTo(BigInteger.valueOf(0)) > 0) {
			throw new GroupAlreadyExistException("Group already mapped with the user");
		}

		UserDetails userDetails = userRepository.getById(userData.getId());
		GroupDetails groupDetails = new GroupDetails();
		groupDetails.setGroupName(groupName);
		userDetails.getGroupDetails().add(groupDetails);
		UserDetails addedUserDetails = userRepository.save(userDetails);
		logger.debug("User Details saved");
		return addedUserDetails != null;
	}

	@Override
	public boolean deleteGroup(int groupId) throws GroupNotFoundException {
		if (!groupRepository.existsById(groupId)) {
			throw new GroupNotFoundException("Group not mapped with the user");
		}

		groupRepository.deleteById(groupId);
		return !groupRepository.existsById(groupId);
	}

	@Override
	public List<GroupDetailsDto> getAllGroup() {

		return (groupRepository.findByUserId(userData.getId())).stream()
				.map(group -> modelMapper.map(group, GroupDetailsDto.class)).collect(Collectors.toList());

	}

	@Override
	public boolean modifyGroupName(int groupId, String newGroupName) throws GroupAlreadyExistException {
		if (groupRepository.existsByGroupNameAndUserId(userData.getId(), newGroupName)
				.compareTo(BigInteger.valueOf(0)) > 0) {
			throw new GroupAlreadyExistException("Already mapped with the user");
		}

		GroupDetails groupDetails = groupRepository.getById(groupId);
		groupDetails.setGroupName(newGroupName);

		return groupRepository.save(groupDetails) != null;
	}

	@Override
	public GroupDetailsDto getGroupByName(String groupName) throws GroupNotFoundException {
		if (groupRepository.existsByGroupNameAndUserId(userData.getId(), groupName)
				.compareTo(BigInteger.valueOf(0)) == 0) {
			throw new GroupNotFoundException("Group not mapped with the user");
		}
		return modelMapper.map(groupRepository.findByGroupNameAndUserId(userData.getId(), groupName),
				GroupDetailsDto.class);
	}
}
