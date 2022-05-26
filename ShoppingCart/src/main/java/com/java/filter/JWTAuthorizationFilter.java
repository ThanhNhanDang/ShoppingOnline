package com.java.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.java.contants.SecurityConstants;

import io.jsonwebtoken.Jwts;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	private UserDetailsService userDetailsService;
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//Lấy chuỗi token từ header của request
		String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
		//kiểm tra xem chuỗi token đã được đính kèm vào request hay chưa
		//và có đúng định dạng hay không (token phải bắt đầu bằng bearer)
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			//Thay thế "Bearer " bằng chuỗi "" để lấy chuỗi token chính xác
			String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");
			//Giải mã token lấy email
			String username = Jwts.parser() 
					.setSigningKey(SecurityConstants.tokenSecret)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			//Lấy thông tin user từ database 
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToke = 
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToke);
		}
		
		chain.doFilter(request, response);
	}
}
