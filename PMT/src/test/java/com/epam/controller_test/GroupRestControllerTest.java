package com.epam.controller_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.api.GroupService;
import com.epam.controller.GroupRestController;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.UserData;
import com.epam.dto.UserDetailsDto;
import com.fasterxml.jackson.core.JsonProcessingException;

@WebMvcTest(GroupRestController.class)
@ContextConfiguration(classes = { GroupRestController.class })
class GroupRestControllerTest extends JsonHandler {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private GroupService groupService;

	@MockBean
	private UserData userData;
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
		String uri = "/pmt/groups/1";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(super.mapFromJson(content, GroupDetailsDto[].class).length > 0);
	}

	@Test
	void testAddGroup() throws Exception {
		when(groupService.addGroup("Qwerty")).thenReturn(true);
		String uri = "/pmt/groups/addGroup?groupName=Qwerty&userId=1";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("true", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void testUpdateGroupName() throws JsonProcessingException, Exception {
		when(groupService.modifyGroupName(2, "google")).thenReturn(true);
		String uri = "/pmt/groups/updateGroup";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapToJson(userDetails.getGroupDetailsDto().get(0))))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(200, status);
		boolean isUpdated = mapFromJson(content, Boolean.class);
		assertEquals(true, isUpdated);
	}

	@Test
	void testDeleteGroup() throws JsonProcessingException, Exception {
		when(groupService.deleteGroup(2)).thenReturn(true);
		String uri = "/pmt/groups//deleteGroup/2";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(200, status);
		boolean isDeleted = mapFromJson(content, Boolean.class);
		assertEquals(true, isDeleted);
	}

	@Test
	void testSearchGroup() throws Exception {
		when(groupService.deleteGroup(2)).thenReturn(true);
		String uri = "/pmt/groups/search?userId=1&groupName=outlook";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(200, status);
		assertTrue(mapFromJson(content, GroupDetailsDto[].class).length > 0);
	}

}
