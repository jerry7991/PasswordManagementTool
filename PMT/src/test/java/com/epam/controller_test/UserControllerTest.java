package com.epam.controller_test;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.epam.api.UserService;
import com.epam.controller.UserController;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.AccountDetail;
import com.epam.entities.GroupDetails;
import com.epam.entities.UserDetails;

@ContextConfiguration(classes = { UserController.class })
@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class UserControllerTest extends JsonHandler {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@MockBean
	private UserService userService;

	private UserDetails userDetails;
	private List<AccountDetail> accounts;
	private GroupDetails groupDetails;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
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
		mockMvc.perform(post("/login?userName=gmail&password=Gmail@123").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isForbidden()).andExpect(view().name("error"));
	}

	@Test
	void testAddUser() throws Exception {
		UserData userData = new UserData();
		userData.setUserName(userDetails.getUserName());
		userData.setPassword(userDetails.getMasterPassword());
		when(userService.addUser(userDetails)).thenReturn(true);

		mockMvc.perform(
				post("/creatUser").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(mapToJson(userData)))
				.andExpect(status().isForbidden());
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
