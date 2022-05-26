package com.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.java.entity.Role;
import com.java.service.RoleService;


@SpringBootApplication(scanBasePackages  = { " com.java " })
public class ShoppingCartApplication {
	private RoleService roleService;
	public ShoppingCartApplication(RoleService roleService) {
		this.roleService = roleService;
	}
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}
	
	@Bean
	public CorsFilter corsFilter() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_ADMIN", "Administrators"));
		roles.add(new Role("ROLE_USER", "User"));
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:4401"));
		configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin","Content-Type","Accept"
				,"Authorization", "Origin,Accept","X-Requested-With","Access-Control-Request-Method","Access-Control-Request-Headers"));
		configuration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept"
				,"Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource basedCorsConfigurationSource= new UrlBasedCorsConfigurationSource();
		basedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
		this.roleService.saveAll(roles);
		return new CorsFilter(basedCorsConfigurationSource);
	}
}
