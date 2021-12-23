package com.epam.service_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.epam.dto.UserData;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.repositories.GroupRepository;
import com.epam.repositories.UserRepository;
import com.epam.service.GroupServiceImpl;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

	@Mock
	GroupRepository groupRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	private Validation validation;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private GroupServiceImpl groupServiceImpl;

	@Mock
	private UserData userData;
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
	void testAddGroup() throws GroupAlreadyExistException {
		when(userRepository.getById(userData.getId())).thenReturn(userDetails);
		when(userRepository.save(userDetails)).thenReturn(userDetails);
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(BigInteger.valueOf(0));
		assertTrue(groupServiceImpl.addGroup("yahoo"));
	}

	@Test
	void testAddGroupException() throws GroupAlreadyExistException {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "google")).thenReturn(new BigInteger("1"));
		GroupAlreadyExistException exception = assertThrows(GroupAlreadyExistException.class, () -> {
			groupServiceImpl.addGroup("google");
		});
		assertEquals("Group already mapped with the user", exception.getMessage());
	}

	@Test
	void testDeleteGroup() throws GroupNotFoundException {
		when(groupRepository.existsById(0)).thenReturn(true);
		assertTrue(!groupServiceImpl.deleteGroup(0));
	}

	@Test
	void testDeleteGroupEx() {
		when(groupRepository.existsById(0)).thenReturn(false);
		GroupNotFoundException exception = assertThrows(GroupNotFoundException.class, () -> {
			groupServiceImpl.deleteGroup(0);
		});
		assertEquals("Group not mapped with the user", exception.getMessage());
	}

	@Test
	void testGetAllGroup() {
		when(groupRepository.findByUserId(userData.getId())).thenReturn(userDetails.getGroupDetails());
		when(modelMapper.map(groupDetails, GroupDetailsDto.class))
				.thenReturn(localMapper.map(groupDetails, GroupDetailsDto.class));
		assertNotNull(groupServiceImpl.getAllGroup());
	}

	@Test
	void testModifyGroupName() throws GroupAlreadyExistException {
		GroupDetails groupDetails = userDetails.getGroupDetails().get(0);
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(BigInteger.valueOf(0));
		when(groupRepository.getById(groupDetails.getGroupId())).thenReturn(groupDetails);
		groupServiceImpl.modifyGroupName(groupDetails.getGroupId(), "yahoo");
		assertEquals("yahoo", groupDetails.getGroupName());
	}

	@Test
	void testModifyGroupNameEx() {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(new BigInteger("1"));
		GroupAlreadyExistException exception = assertThrows(GroupAlreadyExistException.class, () -> {
			groupServiceImpl.modifyGroupName(userData.getId(), "yahoo");
		});
		assertEquals("Already mapped with the user", exception.getMessage());
	}

	@Test
	void testGetGroupByName() throws GroupNotFoundException {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(BigInteger.valueOf(1));
		assertNull(groupServiceImpl.getGroupByName("yahoo"));
	}

	@Test
	void testGetGroupByNameEx() throws GroupNotFoundException {
		when(groupRepository.existsByGroupNameAndUserId(userData.getId(), "yahoo")).thenReturn(BigInteger.valueOf(0));
		GroupNotFoundException exception = assertThrows(GroupNotFoundException.class, () -> {
			groupServiceImpl.getGroupByName("yahoo");
		});
		assertEquals("Group not mapped with the user", exception.getMessage());
	}

	@Test
	void testDeleteGroupById() throws GroupNotFoundException {
		when(groupRepository.existsById(1)).thenReturn(true);
		assertTrue(!groupServiceImpl.deleteGroup(1));
	}

	@Test
	void testDeleteGroupByIdEx() throws GroupNotFoundException {
		when(groupRepository.existsById(1)).thenReturn(false);
		GroupNotFoundException exception = assertThrows(GroupNotFoundException.class, () -> {
			groupServiceImpl.deleteGroup(1);
		});
		assertEquals("Group not mapped with the user", exception.getMessage());
	}

}
