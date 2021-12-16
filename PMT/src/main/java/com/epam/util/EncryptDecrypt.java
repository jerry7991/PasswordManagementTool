package com.epam.util;

import java.security.InvalidKeyException;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class EncryptDecrypt {

	private Cipher cipher;

	private SecretKey secretKey;

	@PostConstruct
	public void initilizeAlgo() {
		try {

			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			secretKey = keyGenerator.generateKey();

			cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDecrypted(String encryptedText) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		String decryptedText = "";
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
			decryptedText = new String(decryptedByte);
		} catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	public String getEncrypted(String plainTxt) {
		byte[] plainTextByte = plainTxt.getBytes();
		String encryptedText = "";
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedText = encoder.encodeToString(encryptedByte);
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}
}
