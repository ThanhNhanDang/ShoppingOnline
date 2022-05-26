package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.entity.ResetPasswordTokenEntity;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordTokenEntity, Long> {

}
