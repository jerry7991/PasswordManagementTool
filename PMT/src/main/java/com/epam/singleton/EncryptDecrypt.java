package com.epam.singleton;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptDecrypt {

	private static EncryptDecrypt instance;

	private static Cipher cipher;

	private static PublicKey publicKey;

	private static KeyPair pair;
	private static SecretKey secretKey;

	private EncryptDecrypt() {

	}

	private void initilizeAlgo() {
		try {
			Signature sign = Signature.getInstance("SHA256withRSA");

			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128); // block size is 128bits
			secretKey = keyGenerator.generateKey();

			cipher = Cipher.getInstance("AES"); // SunJCE provider AES algorithm, mode(optional) and padding
												// schema(optional)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static EncryptDecrypt getInstance() {
		if (instance == null) {
			instance = new EncryptDecrypt();
			instance.initilizeAlgo();
		}
		return instance;
	}

	public static String getDecrypted(String encryptedText) {
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

	public static String getEncrypted(String plainTxt) {
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
		System.out.println("Password\n" + encryptedText);
		return encryptedText;
	}
}
