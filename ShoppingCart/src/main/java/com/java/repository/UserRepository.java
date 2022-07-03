package com.java.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.UserDto;
import com.java.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u WHERE u.email = :email")
	User checkEmail (@Param("email") String email);
	
	@Query("select new com.java.dto.UserDto(u.id, u.name, u.email, u.created_at, u.address, u.is_email_verfied, u.mobile, u.image_url, r.id, r.description, u.deliveryAddressId, u.gender) from User u, Role r Where CONCAT(u.name, ' ', u.email, ' ', u.gender, ' ', r.description) LIKE %:key% and u.role=r")
	List<UserDto> search(@Param("key") String key);
	
	@Query("select new com.java.dto.UserDto(u.id, u.name, u.email, u.created_at, u.address, u.is_email_verfied, u.mobile, u.image_url, r.id, r.description, u.deliveryAddressId, u.gender) from User u, Role r Where u.role=r")
	List<UserDto> FindAllByDelete();
	
	@Transactional
	@Modifying
	@Query("Update User u set u.is_email_verfied = :is_email_verfied where u.id = :id")
	void updateActive(@Param("id")long id,@Param("is_email_verfied") boolean is_email);
}
