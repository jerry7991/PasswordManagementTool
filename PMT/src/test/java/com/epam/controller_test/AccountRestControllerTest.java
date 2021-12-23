package com.epam.controller_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.epam.api.AccountService;
import com.epam.controller.AccountRestController;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.UserDetailsDto;
import com.fasterxml.jackson.core.JsonProcessingException;

@WebMvcTest(AccountRestController.class)
@ContextConfiguration(classes = { AccountRestController.class })
class AccountRestControllerTest extends JsonHandler {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

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
	void testGetAccounts() throws Exception {
		String uri = "/pmt/groups/accounts/1";

		when(accountService.findAccountByGroupId(1)).thenReturn(accounts);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		AccountDetailDto[] accountJson = mapFromJson(content, AccountDetailDto[].class);
		assertTrue(accountJson.length > 0);
	}

	@Test
	void testAddAccount() throws Exception {

		when(accountService.addAccount(any(AccountDetailDto.class))).thenReturn(true);

		System.out.println("accountService.addAccount(accounts.get(0)) " + accountService.addAccount(accounts.get(0)));
		String uri = "/pmt/groups/accounts/addAccount";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(accounts.get(0)))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println("Contentssss :: " + content);
		assertEquals(200, status);
		boolean isAdded = mapFromJson(content, Boolean.class);
		assertEquals(true, isAdded);
	}

	@Test
	void testUpdateAccount() throws JsonProcessingException, Exception {

		when(accountService.updateAccount(any(AccountDetailDto.class))).thenReturn(true);
		String uri = "/pmt/groups/accounts/updateAccount";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(accounts.get(0)))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(200, status);
		boolean isUpdated = mapFromJson(content, Boolean.class);
		assertEquals(true, isUpdated);
	}

	@Test
	void testDeleteAccount() throws JsonProcessingException, Exception {
		when(accountService.deleteAccountById(any(AccountDetailDto.class))).thenReturn(true);
		String uri = "/pmt/groups/accounts/deleteAccount";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(accounts.get(0)))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(200, status);
		boolean isDeleted = mapFromJson(content, Boolean.class);
		assertEquals(true, isDeleted);
	}

	@Test
	void testShowAccount() throws JsonProcessingException, Exception {
		when(accountService.findAccountByAccountId(6)).thenReturn(accounts.get(0));
		String uri = "/pmt/groups/accounts/showAccountByAccountID?accountId=6&groupId=4";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(200, status);
		assertEquals(mapToJson(accounts.get(0)), content);
	}

}
