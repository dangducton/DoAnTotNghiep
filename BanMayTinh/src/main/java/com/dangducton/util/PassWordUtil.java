package com.dangducton.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassWordUtil {

	static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static String getEncoderPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
