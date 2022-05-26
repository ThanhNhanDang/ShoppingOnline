package com.java.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.java.dto.ChangePasswordDto;
import com.java.dto.TokenDto;
import com.java.dto.UserDto;
import com.java.entity.User;
import com.java.model.RegisterByAdmin;

public interface UserService extends BaseService<Integer, UserDto, Long>{
	UserDto findByEmail(String email) throws Exception;
	List<UserDto> search(String key);
	void addByAdmin (RegisterByAdmin admin) throws Exception;
	void saveReturn (UserDto dto) throws Exception;
	List<UserDto>deleteDtos(Long id) throws Exception;
	int editMyAccout(UserDto dto) throws Exception;
	int editMyAccoutByAdmin(UserDto dto) throws Exception;
	int updateActive(UserDto dto) throws Exception;
	void sendVerificationEmail(User dto) throws UnsupportedEncodingException, MessagingException;
	
	void checkCode(String code, String email) throws Exception;
	void signUpWithSocialMediaGoogle(TokenDto dto) throws IOException;
	void signUpWithSocialMediaFacebook(TokenDto dto) throws IOException, MessagingException;
	Payload signInGoogle(TokenDto dto) throws IOException;
	org.springframework.social.facebook.api.User signInFacebook(TokenDto dto) throws IOException;
	
	void changePassword(ChangePasswordDto dto) throws Exception;
}
