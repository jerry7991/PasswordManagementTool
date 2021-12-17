package com.epam.service_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.api.Validation;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;
import com.epam.repositories.GroupRepository;
import com.epam.repositories.UserRepository;
import com.epam.service.GroupServiceImpl;
import com.epam.util.Loggers;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

	@Mock
	GroupRepository groupRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	private Loggers LOGGER;

	@Mock
	private Validation validation;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private GroupServiceImpl groupServiceImpl;

	@Mock
	private static UserData userData;
	private static UserDetails userDetails;
	private GroupDetails groupDetails;
	private ModelMapper localMapper;

	@BeforeEach
	public void init() {
		localMapper = new ModelMapper();
		groupDetails = new GroupDetails();
		userDetails = new UserDetails();
		userDetails.setUserId(1);

		userData.setId(1);
		userData.setUserName("admin");
		userData.setPassword("Admin@123");
		groupDetails.setGroupId(2);
		groupDetails.setGroupName("google");
		userDetails.setUserName("admin");
		userDetails.setMasterPassword("Admin@123");
		List<GroupDetails> groups = new ArrayList<>();
		groups.add(groupDetails);
		userDetails.setGroupDetails(groups);
	}

	@Test
	void testAddGroup() {
		when(validation.isValidName("yahoo")).thenReturn(new Response(true, "valid"));
		when(userRepository.getById(userData.getId())).thenReturn(userDetails);
		when(userRepository.save(userDetails)).thenReturn(userDetails);
		assertTrue(groupServiceImpl.addGroup("yahoo"));
	}

	@Test
	void testAddGroupEx() {
		when(validation.isValidName("yahoo")).thenReturn(new Response(false, "Invalid"));
		assertFalse(groupServiceImpl.addGroup("yahoo"));
	}

	@Test
	void testDeleteGroup() {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(new BigInteger("1"));
		assertTrue(groupServiceImpl.deleteGroup(userData.getId(), "yahoo"));
	}

	@Test
	void testDeleteGroupEx() {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(new BigInteger("0"));
		assertFalse(groupServiceImpl.deleteGroup(userData.getId(), "yahoo"));
	}

	@Test
	void testGetAllGroup() {
		when(groupRepository.findByUserId(userData.getId())).thenReturn(userDetails.getGroupDetails());
		when(modelMapper.map(groupDetails, GroupDetailsDto.class))
				.thenReturn(localMapper.map(groupDetails, GroupDetailsDto.class));
		assertNotNull(groupServiceImpl.getAllGroup());
	}

	@Test
	void testModifyGroupName() {
		GroupDetails groupDetails = userDetails.getGroupDetails().get(0);
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(new BigInteger("0"));
		when(groupRepository.getById(groupDetails.getGroupId())).thenReturn(groupDetails);
		groupServiceImpl.modifyGroupName(groupDetails.getGroupId(), "yahoo");
		assertEquals("yahoo", groupDetails.getGroupName());
	}

	@Test
	void testModifyGroupNameEx() {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(new BigInteger("1"));
		assertFalse(groupServiceImpl.modifyGroupName(userData.getId(), "yahoo").isStatus());
	}

	@Test
	void testGetGroupByName() {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(null);
		assertNull(groupServiceImpl.getGroupByName("yahoo"));
	}

	@Test
	void testGetGroupByNameEx() {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(new BigInteger("1"));
		assertNull(groupServiceImpl.getGroupByName("yahoo"));
	}

}
