package com.java.service;

import com.java.dto.UserDto;

public interface PasswordService {
	boolean checkEmailResetPassword(String email) throws Exception;
	
	
	void forgottenPassword(String email) throws Exception;
	void updatePassword(String password, String token) throws Exception;
	
	void sendResetPasswordEmail(UserDto dto);
}
