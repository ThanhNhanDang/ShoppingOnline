package com.java.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.java.dto.EmailDto;

@Configuration
@EnableScheduling
public class WebConfig {
	private final RestTemplate restTemplate = new RestTemplate();
	private final String url = "https://doan1thanhnhanvituong.herokuapp.com";

	@Scheduled(fixedDelay = 25 * 60 * 1000)
	public void keepAwakeEmail() {
		String pingUrl = this.url + "/ping";
		String result = restTemplate.getForObject(pingUrl, String.class);
		System.out.println(result);
	}

	public String sendEmailResetPassword(EmailDto dto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		String emailUrl = this.url + "/doan1/email/send/estore";
		HttpEntity<EmailDto> emailDto = new HttpEntity<EmailDto>(dto,headers);
		return restTemplate.exchange(emailUrl, HttpMethod.POST, emailDto, String.class).getBody();
	}
}