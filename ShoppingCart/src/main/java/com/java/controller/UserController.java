package com.java.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.config.ShoppingConfig;
import com.java.dto.ChangePasswordDto;
import com.java.dto.GenericResponseDto;
import com.java.dto.ResetPasswordDto;
import com.java.dto.UserDto;
import com.java.model.Message;
import com.java.model.RegisterByAdmin;
import com.java.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/profile/{email}")
	public ResponseEntity<UserDto> get(@PathVariable String email) {
		try {
			UserDto dto = service.findByEmail(email);
			if (dto == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/profile")
	public ResponseEntity<?> get(@RequestBody HashMap<String, String> request) {
		try {
			String keys[] = { "userId" };
			if (ShoppingConfig.validationWithHashMap(keys, request)) {
			}
			long userId = Long.valueOf(request.get("userId"));

			UserDto dto = service.findById(userId);
			if (dto == null) {
				return new ResponseEntity<>(
						new Message("No Content", Instant.now(), "201", "No Content", "/api/user/profile"),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> get() {
		try {
			List<UserDto> dtos = service.findAll();
			if (dtos.isEmpty())
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public Object upload(@RequestBody UserDto dto) {
		try {
			return new ResponseEntity<Object>(service.editMyAccount(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/update"),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/update/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public Object updateFile(@RequestParam String dto, @RequestParam MultipartFile file) {
		try {
			return new ResponseEntity<Object>(service.editMyAccount(dto, file), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/update"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/active-admin")
	public ResponseEntity<?> updateActive(@RequestBody UserDto dto) {
		try {
			service.updateActive(dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/update/active-admin"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/update/admin")
	public Object updateAdmin(@RequestBody UserDto dto) {
		try {
			service.editMyAccoutByAdmin(dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/update/admin"),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/update/admin/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public Object updateAdminFile(@RequestParam String dto, @RequestParam MultipartFile file) {
		try {
			service.editMyAccoutFileByAdmin(dto, file);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/update/admin"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/search")
	public @ResponseBody List<UserDto> search(@RequestParam String key) {
		List<UserDto> dtos = service.search(key);
		return dtos;
	}

	@DeleteMapping("/delete-not-return")
	public ResponseEntity<?> deleteNotReturn(@RequestParam("id") long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/delete-not-return"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam("id") long id) {
		try {
			List<UserDto> dtos = service.deleteDtos(id);
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/delete"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/add/by-admin")
	public ResponseEntity<?> signupByAdmin(@RequestBody RegisterByAdmin registerByAdmin) {
		try {
			service.addByAdmin(registerByAdmin);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/add/by-admin"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto dto) {
		try {
			service.changePassword(dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/change-password"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto dto) {
		try {
			GenericResponseDto genericResponseDto = service.validatePasswordResetToken(dto.getToken());
			if (genericResponseDto.getMessage() != null) {
				genericResponseDto.setError(genericResponseDto.getMessage());
				return new ResponseEntity<>(genericResponseDto, HttpStatus.BAD_REQUEST);
			}

			genericResponseDto = service.passwordReset(dto);
			return new ResponseEntity<>(genericResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/change-password"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/validate-token-password")
	public ResponseEntity<?> validateTokenPassword(@RequestParam("token") String token) {
		try {
			GenericResponseDto result = service.validatePasswordResetToken(token);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage(), Instant.now(), "400", "Bad Request",
					"/api/user/validate-token-password"), HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/signup/active")
	public ResponseEntity<?> signupActive(@RequestParam("email") String email, @RequestParam("code") String code) {
		try {
			service.checkCode(code, email);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new Message(e.getMessage(), Instant.now(), "400", "Bad Request", "/api/user/signup/active"),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<GenericResponseDto> resetPassword(HttpServletRequest request,
			@RequestParam("email") String email) {
		try {
			GenericResponseDto genericResponseDto = this.service.resetPassword(request, email);
			return new ResponseEntity<GenericResponseDto>(genericResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GenericResponseDto>(new GenericResponseDto(e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

}
