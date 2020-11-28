package com.sdi.monitoring.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class AES256Cipher {
	final static String secretKey = "ssoms292818!!fjfjksmsdwqe2we!!qw";
	static String IV = secretKey.substring(0, 16);

	public static String AESEncode(String input) {
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
			byte[] encrypted = c.doFinal(input.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(encrypted));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String AESDecode(String input) {
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));
			byte[] byteStr = Base64.decodeBase64(input.getBytes());
			return new String(c.doFinal(byteStr), "UTF-8");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
