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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.api.AccountService;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.UserDetailsDto;

//@WebMvcTest(AccountController.class)
//@ContextConfiguration(classes = { AccountController.class })
@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
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
		when(accountService.findAccountByGroupId(1)).thenReturn(accounts);
		mockMvc.perform(get("/getAccount?groupId=1")).andExpect(status().isOk()).andExpect(view().name("getAccount"));
	}

	@Test
	void testAddAccount() throws Exception {
		when(accountService.addAccount(accounts.get(0))).thenReturn(true);
		mockMvc.perform(
				post("/addAccount?accountId=1&accountName=gmail&groupId=1&url=https://url.com&password=Gmail@1234")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("getAccount"));
	}

	@Test
	void testUpdateAccount() throws Exception {
		when(accountService.updateAccount(accounts.get(0))).thenReturn(true);
		mockMvc.perform(
				post("/updateAccount?accountName=gmail&url=https://url.com&password=Gmail@1234&accountId=1&groupId=1")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("getAccount"));
	}

	@Test
	void testDeleteAccount() throws Exception {
		when(accountService.updateAccount(accounts.get(0))).thenReturn(true);
		mockMvc.perform(
				post("/deleteAccount?accountName=gmail&url=https://url.com&password=Gmail@1234&accountId=1&groupId=1")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("getAccount"));
	}

	@Test
	void testShowAccount() throws Exception {
		when(accountService.findAccountByAccountId(1)).thenReturn(accounts.get(0));
		mockMvc.perform(get("/showAccountByAccountID?accountId=1&groupId=2")).andExpect(status().isOk())
				.andExpect(view().name("ShowUpdateAccount"));
	}
}