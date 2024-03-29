package com.java.service.impl;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.java.contants.ImageConstants;
import com.java.contants.SecurityConstants;
import com.java.dto.ChangePasswordDto;
import com.java.dto.EmailDto;
import com.java.dto.GenericResponseDto;
import com.java.dto.ResetPasswordDto;
import com.java.dto.TokenDto;
import com.java.dto.UserDto;
import com.java.entity.ResetPasswordTokenEntity;
import com.java.entity.User;
import com.java.model.RegisterByAdmin;
import com.java.repository.ResetPasswordRepository;
import com.java.repository.UserRepository;
import com.java.service.FileService;
import com.java.service.UserService;

import net.bytebuddy.utility.RandomString;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepo;
	private ResetPasswordRepository resetPassRepo;
	private FileService fileService;
	private Clock cl = Clock.systemDefaultZone();

	public UserServiceImpl(ResetPasswordRepository resetPassRepo, AuthenticationManager authenticationManager,
			UserRepository userRepo, FileService fileService) {
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.fileService = fileService;
		this.resetPassRepo = resetPassRepo;

	}
	
	private UserDto jsonMapper (String dto) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(dto, UserDto.class); 
	}
	
	private User mapperEntity(UserDto dto) throws Exception {
		if (!userRepo.existsById(dto.getId()))
			throw new Exception("User not found");
		User entityTemp = userRepo.checkEmail(dto.getEmail());

		// nếu entityTemp có tồn tại nhưng id lại khác với id của người update thì => đã
		// có người đăng ký email này r
		if (entityTemp != null && entityTemp.getId() != dto.getId()) {
			throw new Exception("This email already exists");
		}
		User entity = userRepo.findById(dto.getId()).get();
		entity.setEmail(dto.getEmail());
		entity.setMobile(dto.getMobile());
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		entity.setGender(dto.getGender());
		entity.setIs_email_verfied(dto.getIs_email_verfied());
		return entity;
	}
	private UserDto mapperDto(User entity) {
		UserDto dto = new UserDto(entity.getId(), entity.getName(), entity.getEmail(), "***",
				entity.getCreated_at(), entity.getLogin_token(), entity.getType(), entity.getAddress(),
				entity.getIs_email_verfied(), entity.getMobile(), entity.getImage_url(), entity.getRole_id(),
				entity.getDeliveryAddressId());
		dto.setNameRole(entity.getRole().getDescription());
		dto.setGender(entity.getGender());
		return dto;
	}
	

	@Override
	public List<UserDto> findAll() {
		List<User> list = userRepo.findAll();
		List<UserDto> dtos = new ArrayList<UserDto>();

		for (User entity : list) {
			UserDto dto = this.mapperDto(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public UserDto findById(Long id) {
		if (!userRepo.existsById(id))
			return null;
		User entity = userRepo.findById(id).get();
		UserDto dto = this.mapperDto(entity);
		dto.setCreated_at(null);
		return dto;
	}

	@Transactional
	@Modifying
	@Override
	public User saveReturn(UserDto dto) throws Exception {
		if (userRepo.checkEmail(dto.getEmail()) != null)
			throw new Exception("Email already in use");
		User entity = new User();
		String hashedPass = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
		entity.setLogin_token(dto.getLogin_token());
		entity.setEmail(dto.getEmail());
		entity.setMobile(dto.getMobile());
		entity.setName(dto.getName());
		entity.setPassword(hashedPass);
		entity.setCreated_at(Instant.now(cl));
		if (dto.getEmail().equalsIgnoreCase("yesthanhnhan16@gmail.com"))
			entity.setRole_id(1);
		else
			entity.setRole_id(2);
		entity.setGender(dto.getGender());
		entity.setImage_url("userDefault.png");
		if (userRepo.save(entity) == null)
			throw new Exception("Unable to register an account, please contact the administrator for assistance.");
		return entity;
	}

	@Override
	public UserDto findByEmail(String email) throws Exception {
		User entity = userRepo.checkEmail(email);
		if (entity == null)
			throw new Exception("User not found");
		if (!entity.getIs_email_verfied())
			throw new Exception("Account has not been activated!");
		UserDto dto = this.mapperDto(entity);
		return dto;
	}
	@Transactional
	@Modifying
	@Override
	public UserDto editMyAccount(UserDto dto) throws Exception {
		User entity = this.mapperEntity(dto);
		entity.setDeliveryAddressId(dto.getDeliveryAddressId());
		if (userRepo.save(entity) == null)
			throw new Exception("Unable to register an account, please contact the administrator for assistance.");
		dto.setImage_url(entity.getImage_url());
		return dto;
	}
	@Transactional
	@Modifying
	@Override
	public UserDto editMyAccount(String dto, MultipartFile file) throws Exception {
		UserDto jsonDto = this.jsonMapper(dto);
		String fileName = UUID.randomUUID()+".png";
		User enUser = this.mapperEntity(jsonDto);
		enUser.setDeliveryAddressId(jsonDto.getDeliveryAddressId());
		fileService.delefile(enUser.getId(), ImageConstants.URL_IMAGE_AVATAR, enUser.getImage_url());
		enUser.setImage_url("avatars"+"/"+jsonDto.getId()+"/"+ fileName);
		fileService.store(file, fileName, 3, enUser.getId());
		if (userRepo.save(enUser) == null)
			throw new Exception("Unable to update");
		jsonDto.setImage_url(enUser.getImage_url());
		return jsonDto;
	}
	@Transactional
	@Modifying
	@Override
	public int updateActive(UserDto dto) throws Exception {
		userRepo.updateActive(dto.getId(), dto.getIs_email_verfied());
		return 0;
	}

	@Override
	public List<UserDto> search(String key) {
		List<UserDto> dtos = userRepo.search(key);
		return dtos;
	}
	@Transactional
	@Modifying
	@Override
	public List<UserDto> deleteDtos(Long id) throws Exception {
		this.delete(id);
		List<UserDto> dtos = userRepo.FindAllByDelete();
		return dtos;
	}

	@Transactional
	@Modifying
	@Override
	public int editMyAccoutByAdmin(UserDto dto) throws Exception {
		User entity = this.mapperEntity(dto);
		entity.setRole_id(dto.getRole_id());
		if (userRepo.save(entity) == null)
			throw new Exception("Unable to update");
		return 0;
	}
	@Transactional
	@Modifying
	@Override
	public void editMyAccoutFileByAdmin(String dto, MultipartFile file) throws Exception {
		UserDto jsonDto = this.jsonMapper(dto);
		String fileName = UUID.randomUUID()+".png";
		User enUser = this.mapperEntity(jsonDto);
		enUser.setRole_id(jsonDto.getRole_id());
		fileService.delefile(enUser.getId(), ImageConstants.URL_IMAGE_AVATAR, enUser.getImage_url());
		enUser.setImage_url("avatars"+"/"+jsonDto.getId()+"/"+ fileName);
		fileService.store(file, fileName, 3, enUser.getId());
		if (userRepo.save(enUser) == null)
			throw new Exception("Unable to update");
		
	}

	@Transactional
	@Modifying
	@Override
	public void addByAdmin(RegisterByAdmin admin) throws Exception {
		if (userRepo.checkEmail(admin.getEmail()) != null)
			throw new Exception("Email already exists");

		User entity = new User();
		String hashedPass = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
		entity.setEmail(admin.getEmail());
		entity.setMobile(admin.getMobile());
		entity.setName(admin.getName());
		entity.setPassword(hashedPass);
		entity.setCreated_at(Instant.now(cl));
		entity.setRole_id(admin.getRole_id());
		entity.setGender(admin.getGender());
		entity.setIs_email_verfied(admin.isIs_email_verfied());
		entity.setImage_url("userDefault.png");
		if (userRepo.save(entity) == null)
			throw new Exception("Can't add");

	}

	@Override
	public Integer save(UserDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkCode(String code, String email) throws Exception {
		// Kiểm tra xem có tồn tại email này không
		User entity = userRepo.checkEmail(email);
		if (entity == null) {
			throw new Exception("User not found.");
		}
		// sau đó kiểm tra xem tài khoản đã được kích hoạt chưa nếu chưa thì mới tiếp
		// tục so sánh với code

		if (entity.getIs_email_verfied() == true)
			throw new Exception("Account has been activated.");

		// so sánh code
		if (!entity.getLogin_token().equals(code))
			throw new Exception("Verification code is incorrect.");

		// Cập kích hoạt tài khoản sau khi code khớp
		entity.setIs_email_verfied(true);
		userRepo.save(entity);
	}

	@Transactional
	@Modifying
	public void signUpGoogle(Payload payload) throws IOException {
		if (userRepo.checkEmail(payload.getEmail()) != null)
			throw new IOException("Email already in use");
		User entity = new User();
		String hashedPass = BCrypt.hashpw(SecurityConstants.passSecret, BCrypt.gensalt());
		entity.setLogin_token(RandomString.make(64));
		entity.setIs_email_verfied(payload.getEmailVerified());
		entity.setEmail(payload.getEmail());
		entity.setName((String) payload.get("name"));
		entity.setPassword(hashedPass);
		entity.setCreated_at(Instant.now(cl));
		if (payload.getEmail().equalsIgnoreCase("yesthanhnhan16@gmail.com"))
			entity.setRole_id(1);
		else
			entity.setRole_id(2);
		entity.setGender("Prefer not to say");
		entity.setImage_url("userDefault.png");
		if (userRepo.save(entity) == null)
			throw new IOException("Unable to register an account, please contact the administrator for assistance.");
	}

	@Transactional
	@Modifying
	public void signUpFacebook(org.springframework.social.facebook.api.User user) throws IOException {
		if (userRepo.checkEmail(user.getEmail()) != null)
			throw new IOException("Email already in use");
		User entity = new User();
		String hashedPass = BCrypt.hashpw(SecurityConstants.passSecret, BCrypt.gensalt());
		entity.setLogin_token(RandomString.make(64));
		entity.setEmail(user.getEmail());
		entity.setName(user.getName());
		entity.setPassword(hashedPass);
		entity.setIs_email_verfied(true);
		entity.setCreated_at(Instant.now(cl));
		if (user.getEmail().equalsIgnoreCase("yesthanhnhan16@gmail.com"))
			entity.setRole_id(1);
		else
			entity.setRole_id(2);
		entity.setGender("Prefer not to say");
		entity.setImage_url("userDefault.png");
		if (userRepo.save(entity) == null)
			throw new IOException("Unable to register an account, please contact the administrator for assistance.");
	}

	@Transactional
	@Modifying
	@Override
	public void signUpWithSocialMediaGoogle(TokenDto dto) throws IOException {
		Payload payload = this.signInGoogle(dto);
		this.signUpGoogle(payload);

	}

	@Transactional
	@Modifying
	@Override
	public void signUpWithSocialMediaFacebook(TokenDto dto) throws IOException {
		org.springframework.social.facebook.api.User user = this.signInFacebook(dto);
		this.signUpFacebook(user);
	}

	@Override
	public Payload signInGoogle(TokenDto dto) throws IOException {
		final NetHttpTransport httpTransport = new NetHttpTransport();
		final JacksonFactory factory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier.Builder builder = new GoogleIdTokenVerifier.Builder(httpTransport, factory)
				.setAudience(Collections.singletonList(SecurityConstants.googleClientId));
		final GoogleIdToken googleIdToken = GoogleIdToken.parse(builder.getJsonFactory(), dto.getValue());
		final GoogleIdToken.Payload payload = googleIdToken.getPayload();
		return payload;
	}

	@Override
	public org.springframework.social.facebook.api.User signInFacebook(TokenDto dto) throws IOException {
		Facebook facebook = new FacebookTemplate(dto.getValue());
		final String[] fields = { "email", "name" };
		org.springframework.social.facebook.api.User user = facebook.fetchObject("me",
				org.springframework.social.facebook.api.User.class, fields);

		return user;
	}

	@Transactional
	@Modifying
	@Override
	public void changePassword(ChangePasswordDto dto) throws Exception {
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getEmail(),
					dto.getOldPassword());
			authenticationManager.authenticate(authentication);
			User entity = userRepo.checkEmail(dto.getEmail());
			entity.setPassword(BCrypt.hashpw(dto.getConfirmPassword(), BCrypt.gensalt()));
			userRepo.save(entity);
		} catch (Exception e) {
			System.out.println("sai");
			throw new Exception("Current password is incorrect!");
		}
	}

	@Override
	public Integer delete(Long id) throws Exception {
		if (!userRepo.existsById(id))
			throw new Exception("User not found");
		fileService.deleFolder(id, ImageConstants.URL_IMAGE_AVATAR);
		userRepo.deleteById(id);
		return null;
	}

	// send email and create Password Reset Token For User

	@Transactional
	@Modifying
	@Override
	public GenericResponseDto resetPassword(HttpServletRequest request, String email) {
		User entity = this.userRepo.checkEmail(email);
		if (entity == null) {
			throw new UsernameNotFoundException("User Not Found!");
		}
		String token = UUID.randomUUID().toString();
		String message = this.sendEmailResetPassword(request.getHeader("referer"), token, entity);
		this.createPasswordResetTokenForUser(entity, token);
		return new GenericResponseDto(message, null);
	}

	@Transactional
	@Modifying
	@Override
	public void createPasswordResetTokenForUser(User entity, String token) {
		ResetPasswordTokenEntity myToken = new ResetPasswordTokenEntity(token, entity,
				Instant.now().plus(5, ChronoUnit.MINUTES));
		this.resetPassRepo.save(myToken);
	}

	private String sendEmailResetPassword(String contextPath, String token, User user) {
		String url = contextPath + "user/change-password?token=" + token;
		EmailDto dto = new EmailDto(SecurityConstants.emailAdmin, user.getEmail(), "Reset Password",
				"<b><p>Dear " + user.getName()
						+ ",</p></b></br><p>Please click the link below to reset your password:</p></br><h3><a href=\""
						+ url + "\">Reset Password</a></h3></br></br><p>Thank you<br>The ESTORE</p>");
		RestTemplate restTemplate = new RestTemplate();
		String url2 = "https://doan1.thanhnhandev.xyz";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		String emailUrl = url2 + "/doan1/email/send/estore";
		HttpEntity<EmailDto> emailDto = new HttpEntity<EmailDto>(dto, headers);
		return restTemplate.exchange(emailUrl, HttpMethod.POST, emailDto, String.class).getBody();
	}
	// send email and create Password Reset Token For User
	////////////////////////
	//////////////
	//////////////

	// change password

	@Override
	public GenericResponseDto validatePasswordResetToken(String token) {
		final ResetPasswordTokenEntity passToken = resetPassRepo.findByToken(token);
		return new GenericResponseDto(
				!isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null, null);
	}

	private boolean isTokenFound(ResetPasswordTokenEntity passToken) {
		return passToken != null;
	}

	private boolean isTokenExpired(ResetPasswordTokenEntity passToken) {
		return passToken.getExpiryDate().isBefore(Instant.now());
	}

	@Override
	public GenericResponseDto passwordReset(ResetPasswordDto resetPasswordDto) {
		User user = resetPassRepo.findByToken(resetPasswordDto.getToken()).getUser();
	    if(user != null) {
	    	user.setPassword(BCrypt.hashpw(resetPasswordDto.getNewPassword(), BCrypt.gensalt()));
			userRepo.save(user);
	        return new GenericResponseDto("Reset password successfully!", null);
	    } else {
	        return new GenericResponseDto("User not found!", null);
	    }
	}

}
