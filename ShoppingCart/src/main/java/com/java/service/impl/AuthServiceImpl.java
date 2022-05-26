package com.java.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.java.contants.SecurityConstants;
import com.java.dto.TokenDto;
import com.java.dto.UserDto;
import com.java.model.AuthenticationResponse;
import com.java.model.LoginRequest;
import com.java.model.RegisterRequest;
import com.java.service.AuthService;
import com.java.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.bytebuddy.utility.RandomString;

@Service
@Scope("prototype")
@Transactional(rollbackOn = Exception.class)
public class AuthServiceImpl implements AuthService {
	private AuthenticationManager authenticationManager;
	private UserService service ;
	public AuthServiceImpl(AuthenticationManager authenticationManager,  UserService service) {
		this.authenticationManager = authenticationManager;
		this.service = service;
	}
	@Override
	public AuthenticationResponse login(LoginRequest dto) throws Exception{
		
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
			authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			//gọi phương thức tạo chuỗi token
			
			//Thời gian có hiệu lực của chuỗi jwt (10 - ngày)
			
			Date now = new Date();
			String token = Jwts.builder()
					.setSubject(dto.getEmail())
					.setIssuedAt(now)
					.setExpiration(new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS256, SecurityConstants.tokenSecret)
					.compact();
			
			UserDto userProfile = service.findByEmail(dto.getEmail());
			return new AuthenticationResponse(token, userProfile);
		} catch (Exception e) {
			throw new Exception("Incorrect email or password");
		}
		
			
			
	}
	@Override
	public void signup(RegisterRequest dto) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setEmail(dto.getEmail());
		userDto.setPassword(dto.getPassword());
		userDto.setName(dto.getName());
		userDto.setMobile(dto.getMobile());
		userDto.setGender(dto.getGender());
		userDto.setLogin_token(RandomString.make(64));
		service.saveReturn(userDto);
		
	}
	
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	@Override
	public AuthenticationResponse loginGoogle(TokenDto dto) throws Exception {
		Payload payload = service.signInGoogle(dto);
		LoginRequest loginRequest = new LoginRequest(payload.getEmail(), SecurityConstants.passSecret);
		AuthenticationResponse response = this.login(loginRequest);
		
		return response;
	}
	@Override
	public AuthenticationResponse loginFacebook(TokenDto dto) throws Exception {
		User user = service.signInFacebook(dto);
		LoginRequest loginRequest = new LoginRequest(user.getEmail(), SecurityConstants.passSecret);
		AuthenticationResponse response = this.login(loginRequest);
		return response;
	}
	
}	
