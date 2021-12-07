package com.epam.PMT;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.epam.api.Validation;
import com.epam.service.ValidationImpl;
import com.epam.singleton.Loggers;

@TestMethodOrder(OrderAnnotation.class)
public class ValidationTest {
	private static Loggers LOGGER;
	private static Validation validation;

	@BeforeAll
	public static void init() {
		LOGGER = Loggers.getLogger();
		validation = new ValidationImpl();
	}

	@Test
	public void isValidPassword() {
		List<String> input = new ArrayList<String>();
		Collections.addAll(input, new String[] { "xyz", "123", "Anup@1234" });

		long countInvalid = input.stream().filter(password -> !validation.isValidPassword(password).getStatus())
				.count();
		assertTrue(countInvalid == 2);
	}

	@Test
	public void isGroupNameValid() {
		List<String> input = new ArrayList<String>();
		Collections.addAll(input, new String[] { "xyz", "123", "12312", "Anup", "singh" });

		long countInvalid = input.stream().filter(name -> !validation.isGroupNameValid(name).getStatus()).count();
		assertTrue(countInvalid == 2);
	}

	@Test
	public void isValidUrl() {
		List<String> urls = new ArrayList<String>();
		Collections.addAll(urls, new String[] { "xyz", "www.com", "https://", "https://www.google.com", "singh" });

		long countInvalid = urls.stream().filter(url -> !validation.isCorrectUrl(url).getStatus()).count();
		assertTrue(countInvalid == 4);
	}
}
