package com.java.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.dto.UserDetailsDto;
import com.java.entity.User;
import com.java.repository.RoleRepository;
import com.java.repository.UserRepository;

@Service
@Scope("prototype")
public class UserDetailsServiceImpl implements UserDetailsService{
	private UserRepository repository;
	private RoleRepository rolerepository;
	public UserDetailsServiceImpl (UserRepository repository, RoleRepository rolerepository) {
		this.repository = repository;
		this.rolerepository = rolerepository;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User entity = repository.checkEmail(email);
		if(entity == null) {
			throw new UsernameNotFoundException("Incorrect email or password");
		}
		String roleName = rolerepository.findById(entity.getRole_id()).get().getName();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(roleName));
		//trả về đối tượng có kiểu dữ liệu là UserDetails
		UserDetails userDetails = new UserDetailsDto(entity.getEmail(), entity.getPassword(), authorities);
		return userDetails;
	}
	
}
