package com.myconnector.util;

import java.util.UUID;

public class TokenUtil {

	public static String getToken() {
		UUID uuid = UUID.randomUUID();
		String tokenWithDashes = uuid.toString();
		// We only need a 24 byte hash (without dashes), for now
		String token = tokenWithDashes.replace("-", "");
		return token;
	}

}
