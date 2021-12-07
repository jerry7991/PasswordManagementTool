package com.epam.PMT;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.factory.AccountCreation;
import com.epam.factory.AccountUpdation;
import com.epam.factory.AllDetails;
import com.epam.factory.Factories;
import com.epam.model.Response;

public class AppTest {
	@Mock
	Factories factory;

	@BeforeEach
	public void init() {
		factory = mock(Factories.class);
	}

	@Test
	public void createPasswordAccount() {
		AccountCreation accountCreation = new AccountCreation();

		Response response = new Response(true, accountCreation);

		when(factory.getFactory("add account")).thenReturn(response);
		assertTrue(factory.getFactory("add account").getMsg() instanceof AccountCreation);
	}

	@Test
	public void accountUpdation() {
		AccountUpdation accountUpdation = new AccountUpdation();

		Response response = new Response(true, accountUpdation);

		when(factory.getFactory("update account")).thenReturn(response);
		assertTrue(factory.getFactory("update account").getMsg() instanceof AccountUpdation);
	}

	@Test
	public void AllDetails() {
		AllDetails allDetails = new AllDetails();

		Response response = new Response(true, allDetails);

		when(factory.getFactory("all")).thenReturn(response);
		assertTrue(factory.getFactory("all").getMsg() instanceof AllDetails);
	}
}
