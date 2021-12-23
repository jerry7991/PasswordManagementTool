package com.epam.controller_test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.epam.api.UserService;
import com.epam.controller.UserRestController;
import com.epam.dto.Response;
import com.epam.dto.UserData;
import com.epam.entities.UserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;

@WebMvcTest(UserRestController.class)
@ContextConfiguration(classes = { UserRestController.class })
class UserRestControllerTest extends JsonHandler {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private UserService userService;
	private UserData userData;

	@BeforeEach
	public void init() {
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
