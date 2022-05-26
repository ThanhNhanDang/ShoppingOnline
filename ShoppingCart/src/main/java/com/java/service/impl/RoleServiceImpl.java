package com.java.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.java.dto.RoleDto;
import com.java.entity.Role;
import com.java.repository.RoleRepository;
import com.java.service.RoleService;


@Service
@Scope("prototype")
public class RoleServiceImpl implements RoleService{
	
	private RoleRepository repo;
	
	public RoleServiceImpl(RoleRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<RoleDto> findAll() {
		List<Role> entitis = repo.findAll();
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		for(Role entity : entitis) {
			RoleDto dto = new RoleDto(entity.getId(), entity.getName(), entity.getDescription());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public RoleDto findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(RoleDto dto) {
		
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role checkName(String name) {
		return repo.checkName(name);
	}

	@Override
	public Integer saveAll(List<Role> roles) {
		roles.forEach(role -> {
			if(repo.checkName(role.getName()) == null) {
				repo.save(role);
			}
		});
		return 0;
	}

}
