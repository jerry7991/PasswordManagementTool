package com.epam.common;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.util.EncryptDecrypt;

@ExtendWith(MockitoExtension.class)
class EncryptDecryptTest {
	@Mock
	private EncryptDecrypt encryptDecrypt;

	@Test
	void test() {
		String encrypted = encryptDecrypt.getEncrypted("HelloWorld");
		assertEquals("HelloWorld", encryptDecrypt.getDecrypted(encrypted));
	}
}
