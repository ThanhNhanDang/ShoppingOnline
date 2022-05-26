package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Role checkName (@Param("name") String name);
}
