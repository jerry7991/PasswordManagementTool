package com.epam.service_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.UserData;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;
import com.epam.repositories.UserRepository;
import com.epam.service.UserServiceImpl;
import com.epam.util.Loggers;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private Loggers LOGGER;

	@Mock
	private UserData userData;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	private UserDetails userDetails;

	@BeforeEach
	public void init() {
		GroupDetails groupDetails = new GroupDetails();
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
	void testAddUser() {
		when(userRepository.findByUserName(userDetails.getUserName())).thenReturn(null);
		when(userRepository.existsByUserNameAndMasterPassword(userDetails.getUserName(),
				userDetails.getMasterPassword())).thenReturn(true);
		assertEquals("User Added SuccessFully", userServiceImpl.addUser(userDetails).getMsg());
	}

	@Test
	void testAddUserWithDuplicateUser() {
		when(userRepository.findByUserName(userDetails.getUserName())).thenReturn(userDetails);
		assertEquals("User Already exist.", userServiceImpl.addUser(userDetails).getMsg());
	}

	@Test
	void testUserExist() {
		when(userRepository.existsByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(true);
		assertTrue(userServiceImpl.userExist(userData));
	}

	@Test
	void testGetUserDetails() {
		when(userRepository.findByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(userDetails);
		assertNotNull(userServiceImpl.getUserDetails(userData));
	}

	@Test
	void testGetUserDetailsWithWrongUser() {
		when(userRepository.findByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(null);
		assertNull(userServiceImpl.getUserDetails(userData));
	}

	@Test
	void testLogin() {
		when(userRepository.existsByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(true);
		when(userRepository.findByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(userDetails);
		assertTrue(userServiceImpl.login(userData).isStatus());
	}

	@Test
	void testLoginNotFoundUser() {
		when(userRepository.existsByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(false);
		assertEquals("User Id or password are incorrect", userServiceImpl.login(userData).getMsg());
	}

	@Test
	void testLoginNotFoundUserAgain() {
		when(userRepository.existsByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(true);
		when(userRepository.findByUserNameAndMasterPassword(userData.getUserName(), userData.getPassword()))
				.thenReturn(null);
		assertEquals("User Id or password are incorrect", userServiceImpl.login(userData).getMsg());
	}

}
