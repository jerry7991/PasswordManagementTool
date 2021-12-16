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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.api.UserService;
import com.epam.controller.UserController;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.AccountDetail;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = { UserController.class })
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private UserDetails userDetails;
	private List<AccountDetail> accounts;
	private GroupDetails groupDetails;

	@BeforeEach
	public void init() {
		groupDetails = new GroupDetails();
		userDetails = new UserDetails();
		userDetails.setUserId(1);
		groupDetails.setGroupId(2);
		groupDetails.setGroupName("google");
		userDetails.setUserName("admin");
		userDetails.setMasterPassword("Admin@123");
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setAccountId(2);
		accountDetail.setAccountName("gmail");
		accountDetail.setUrl("https://gmail.com");
		accountDetail.setPassword("Gmail@1234");
		accounts = new ArrayList<>();
		accounts.add(accountDetail);
		groupDetails.setAccounts(accounts);
		List<GroupDetails> groups = new ArrayList<>();
		groups.add(groupDetails);
		userDetails.setGroupDetails(groups);
	}

	@Test
	void testLogin() throws Exception {
		UserData userData = new UserData();
		userData.setUserName("gmail");
		userData.setPassword("Gmail@123");
		Response response = new Response(true, "Autherized");
		when(userService.login(userData)).thenReturn(response);
		mockMvc.perform(post("/login?userName=gmail&password=Gmail@123")).andExpect(status().isOk())
				.andExpect(view().name("error"));
	}

	@Test
	void testAddUser() throws Exception {
		Response response = new Response(true, userDetails);
		when(userService.addUser(userDetails)).thenReturn(response);
		mockMvc.perform(post("/creatUser?userName=gmail&password=Gmail@1234")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("home"));
	}

	@Test
	void testHome() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	@Test
	void testAddNewUser() throws Exception {
		mockMvc.perform(get("/addUser")).andExpect(status().isOk()).andExpect(view().name("AddUser"));
	}

}
