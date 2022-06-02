package com.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.java.filter.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private UserDetailsService userDetailsService;
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
		
	@Bean
	@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin()
		.httpStrictTransportSecurity().disable();
		http
		.csrf().disable()
		.antMatcher("/api/**")
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.antMatchers(
				"/api/login/**",
	
				"/api/category/all",
				"/api/product/get-products-by-category/**",
				"/api/product/search/**",
				"/api/product/get-product",
				
				"/api/product-detail/get-product-detail-by-product",

				"/api/review/get/by-product",
				
				"/api/signup/**",
				
				"/api/user/signup/active",
				
				"/api/feedback/save",
				"/api/get/image/**"
				)
		.permitAll()
		
		
		.antMatchers(
				"/api/role/**", 
				
				"/api/user/add/by-admin",
				"/api/user/all",
				"/api/user/add-by-admin",
				"/api/user/update/active-admin",
				"/api/user/update/admin",
				"/api/user/delete/**",
				"/api/user/delete-not-return/**",
				"/api/user/search",
				
				"/api/delete/**",
				
				"/api/category/update-all-category",
				"/api/category/delete",
				"/api/category/add",
				
				"/api/product/add",
				"/api/product/delete/**",
				"/api/product/update",
				"/api/product/search/admin",
				
				"/api/product-detail/add",
				"/api/product-detail/add/image",
				"/api/product-detail/update/image",
				"/api/product-detail/update",
				"/api/product-detail/delete",
				
				"/api/Vietnamese-Administrative-Unit/provinces/save",
				"/api/Vietnamese-Administrative-Unit/provinces/save-all",
				"/api/Vietnamese-Administrative-Unit/provinces/delete",
				"/api/Vietnamese-Administrative-Unit/provinces/update",
				
				"/api/Vietnamese-Administrative-Unit/districts/save",
				"/api/Vietnamese-Administrative-Unit/districts/save-all",
				"/api/Vietnamese-Administrative-Unit/districts/delete",
				"/api/Vietnamese-Administrative-Unit/districts/update",
				
				"/api/Vietnamese-Administrative-Unit/wards/save",
				"/api/Vietnamese-Administrative-Unit/wards/save-all",
				"/api/Vietnamese-Administrative-Unit/wards/delete",
				"/api/Vietnamese-Administrative-Unit/wards/update",
				
				"/api/order/get-all",
				
				"/api/feedback/get-all",
				
				"/api/upload",
				"/dashboad/get-data"
			
				)
		.hasAnyRole("ADMIN")
		.anyRequest()
		.authenticated();
		// Sử dụng JWTSAuthorizationFilter để check token => Lây thông tin người dùng
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService));
		// Cấu hình không sử dụng session lưa thông tin client
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
