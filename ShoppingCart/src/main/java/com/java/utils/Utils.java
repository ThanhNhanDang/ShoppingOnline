package com.java.utils;

import java.util.Date;

import com.java.contants.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Utils {
	public Utils() {}
	
	public String generatedResetPasswordToken(String email) {
		Date now = new Date(); 
		String token = Jwts.builder()
				.setSubject(email)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + SecurityConstants.RESET_PASSWORD_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.tokenSecret)
				.compact();
		
		return token;
	}
}
