package com.java.service;

import java.util.List;

import com.java.dto.RoleDto;
import com.java.entity.Role;

public interface RoleService extends BaseService<Integer, RoleDto, Integer>{
	Role checkName (String name);
	Integer saveAll(List<Role> roles);
}
