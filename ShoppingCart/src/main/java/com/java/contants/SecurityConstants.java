package com.java.contants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
	
	public static final long EXPIRATION_TIME = 864000000L; //10 d
	public static final long RESET_PASSWORD_EXPIRATION_TIME = 3600000L; //1 h

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	public static String tokenSecret;

	public static String passSecret;
	public static String googleClientId;
	
	public SecurityConstants(
			@Value("${app.tokenSecret}") String tokenSecret, 
			@Value("${app.passSecret}") String passSecret,
			@Value("${google.ClientId}") String googleClientId
			) {
		
		SecurityConstants.tokenSecret = tokenSecret;
		SecurityConstants.passSecret = passSecret;
		SecurityConstants.googleClientId = googleClientId;
	}
	
}
