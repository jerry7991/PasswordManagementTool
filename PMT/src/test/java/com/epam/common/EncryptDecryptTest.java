package com.epam.common;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.epam.util.EncryptDecrypt;

class EncryptDecryptTest {

	@Test
	void test() {
		EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
		encryptDecrypt.initilizeAlgo();
		String encrypted = encryptDecrypt.getEncrypted("HelloWorld");
		assertEquals("HelloWorld", encryptDecrypt.getDecrypted(encrypted));
	}
}
