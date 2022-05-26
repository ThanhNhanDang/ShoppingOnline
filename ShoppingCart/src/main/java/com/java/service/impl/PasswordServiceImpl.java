package com.java.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.java.dto.UserDto;
import com.java.entity.ResetPasswordTokenEntity;
import com.java.entity.User;
import com.java.repository.ResetPasswordRepository;
import com.java.repository.UserRepository;
import com.java.service.PasswordService;
import com.java.utils.Utils;

@Service
@Scope("prototype")
public class PasswordServiceImpl implements PasswordService{

	private UserRepository userRepository;
	private ResetPasswordRepository resetPasswordRepository;
	
	public PasswordServiceImpl(UserRepository userRepository, ResetPasswordRepository resetPasswordRepository) {
		this.userRepository = userRepository;
		this.resetPasswordRepository = resetPasswordRepository;
	}

	@Override
	public void forgottenPassword(String email) throws Exception {
	
		
	}

	@Override
	public void updatePassword(String password, String token) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void sendResetPasswordEmail(UserDto dto) {
		
	}

	@Override
	public boolean checkEmailResetPassword(String email) {
		User entity = userRepository.checkEmail(email);
		boolean returnValue = false;
		if(entity == null)
			return returnValue;
		String token = new Utils().generatedResetPasswordToken(entity.getEmail());
		ResetPasswordTokenEntity resetPasswordTokenEntity = new ResetPasswordTokenEntity(token, entity);
		resetPasswordRepository.save(resetPasswordTokenEntity);
		
		return false;
	}
}
