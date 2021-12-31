package com.epam.controller_test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.api.GroupService;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.UserDetailsDto;
import com.epam.repositories.GroupRepository;

//@WebMvcTest(GroupController.class)
//@ContextConfiguration(classes = { GroupController.class })
@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Mock
	GroupRepository groupRepository;

	@MockBean
	private GroupService groupService;

	private UserDetailsDto userDetails;
	private List<AccountDetailDto> accounts;
	private GroupDetailsDto groupDetails;

	@BeforeEach
	public void init() {
		groupDetails = new GroupDetailsDto();
		userDetails = new UserDetailsDto();
		userDetails.setUserId(1);
		groupDetails.setGroupId(2);
		groupDetails.setGroupName("google");
		userDetails.setUserName("admin");
		userDetails.setMasterPassword("Admin@123");
		AccountDetailDto accountDetail = new AccountDetailDto();
		accountDetail.setAccountId(2);
		accountDetail.setAccountName("gmail");
		accountDetail.setUrl("https://gmail.com");
		accountDetail.setPassword("Gmail@1234");
		accounts = new ArrayList<>();
		accounts.add(accountDetail);
		groupDetails.setAccountsDto(accounts);
		List<GroupDetailsDto> groups = new ArrayList<>();
		groups.add(groupDetails);
		userDetails.setGroupDetailsDto(groups);
	}

	@Test
	void testGetGroupDetails() throws Exception {
		when(groupService.getAllGroup()).thenReturn(userDetails.getGroupDetailsDto());
		mockMvc.perform(post("/viewGroupBy").contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("viewGroupBy"));
	}

	@Test
	void testAddGroup() throws Exception {
		when(groupService.addGroup("google")).thenReturn(true);
		when(groupService.getAllGroup()).thenReturn(userDetails.getGroupDetailsDto());
		mockMvc.perform(post("/addGroup?groupName=google").contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("viewGroupBy"));
	}

	@Test
	void testUpdateGroupName() throws Exception {
		when(groupService.addGroup("google")).thenReturn(true);
		mockMvc.perform(
				post("/UpdateGroupName?groupName=google&groupId=1").contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("viewGroupBy"));
	}

	@Test
	void testDeleteGroup() throws Exception {
		when(groupRepository.existsById(1)).thenReturn(false);
		when(groupService.getAllGroup()).thenReturn(userDetails.getGroupDetailsDto());
		mockMvc.perform(get("/deleteGroup?groupName=google&groupId=1")).andExpect(status().isOk())
				.andExpect(view().name("viewGroupBy"));
	}

	@Test
	void testsearchGroup() throws Exception {
		when(groupService.getGroupByName("google")).thenReturn(userDetails.getGroupDetailsDto().get(0));
		mockMvc.perform(post("/searchGroup?groupName=google")).andExpect(status().isOk())
				.andExpect(view().name("viewGroupBy"));
	}
}
