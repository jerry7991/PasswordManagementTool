package com.epam.service_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.AccountDetailDto;
import com.epam.service.ValidationImpl;

@ExtendWith(MockitoExtension.class)
class ValidationTest {

	@InjectMocks
	private ValidationImpl validationImpl;

	@Test
	void isValidPassword() {
		List<String> input = new ArrayList<String>();
		Collections.addAll(input, new String[] { "xyz", "123", "Anup@1234" });

		long countInvalid = input.stream().filter(password -> !validationImpl.isValidPassword(password).isStatus())
				.count();
		assertEquals(2, countInvalid);
	}

	@Test
	void isGroupNameValid() {
		List<String> input = new ArrayList<String>();
		Collections.addAll(input, new String[] { "xyz", "123", "12312", "Anup", "singh" });

		long countInvalid = input.stream().filter(name -> !validationImpl.isValidName(name).isStatus()).count();
		assertEquals(2, countInvalid);
	}

	@Test
	void isValidUrl() {
		List<String> urls = new ArrayList<String>();
		Collections.addAll(urls, new String[] { "xyz", "www.com", "https://", "https://www.google.com", "singh" });

		long countInvalid = urls.stream().filter(url -> !validationImpl.isCorrectUrl(url).isStatus()).count();
		assertEquals(4, countInvalid);
	}

	@Test
	void isAccountValidTest() {
		AccountDetailDto accountDetail = new AccountDetailDto();
		List<String> urls = new ArrayList<String>();
		Collections.addAll(urls, new String[] { "xyz", "www.com", "https://", "https://www.google.com", "singh",
				"https://www.google.com" });
		List<String> name = new ArrayList<String>();
		Collections.addAll(name, new String[] { "xyz", "123", "12312", "Anup", "singh", "anup" });
		List<String> password = new ArrayList<String>();
		Collections.addAll(password, new String[] { "xyz", "123", "Anup@1234", "123444", "xyzsAS@123", "xyzsAS@123" });

		int countInvalid = 0;
		for (int i = 0; i < name.size(); i++) {
			accountDetail.setAccountName(name.get(i));
			accountDetail.setUrl(urls.get(i));
			accountDetail.setPassword(password.get(i));
			countInvalid += validationImpl.isAccountValid(accountDetail).isStatus() ? 1 : 0;
		}
		assertEquals(1, countInvalid);
	}
}
