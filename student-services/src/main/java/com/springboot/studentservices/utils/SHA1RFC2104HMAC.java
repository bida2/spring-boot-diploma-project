package com.springboot.studentservices.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SHA1RFC2104HMAC {
	
		private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
		public static String calculateRFC2104HMAC(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
	        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
	        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
	        mac.init(signingKey);
	        return toHexString(mac.doFinal(data.getBytes()));
	    }


	    private static String toHexString(byte[] bytes) {
	        Formatter formatter = new Formatter();
	        for (byte b : bytes) {
	            formatter.format("%02x", b);
	        }
	        return formatter.toString();
	    }
}
