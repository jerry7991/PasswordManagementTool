package com.epam.controller_test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.epam.api.UserService;
import com.epam.controller.UserRestController;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;

//@WebMvcTest(UserRestController.class)
@ContextConfiguration(classes = { UserRestController.class })
@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class UserRestControllerTest extends JsonHandler {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	private UserService userService;
	private UserData userData;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		userData = new UserData();
		userData.setId(1);
		userData.setPassword("Jerry@1234");
		userData.setUserName("jerry");
	}

	@Test
	void testLogin() throws JsonProcessingException, Exception {
		when(userService.login(any(UserData.class))).thenReturn(new Response(true, "valid user"));
		String uri = "/pmt/login";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(userData))).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	void testAddUser() throws JsonProcessingException, Exception {
		when(userService.addUser(any(UserDetails.class))).thenReturn(true);
		String uri = "/pmt/addUser";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(super.mapToJson(userData))).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("true", mvcResult.getResponse().getContentAsString());
	}

}
