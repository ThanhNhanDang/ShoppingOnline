package com.java.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.entity.DeliveryAddress;
import com.java.entity.Ward;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>{
	@Query("SELECT w FROM Ward w Where w.id = :wardId")
	Ward checkAddress(@Param("wardId") long wardId);
	@Query("SELECT  d from DeliveryAddress d Where d.userId = :userId and d.id = :id")
	DeliveryAddress checkExist(@Param("id") long id, @Param("userId") long userId);
	
	@Query("Select d from DeliveryAddress d Where d.userId = :userId and d.wardId = :wardId")
	DeliveryAddress checkForExist(@Param("userId") long userId, @Param("wardId") long wardId);
	
	@Transactional
	@Modifying
	@Query("Delete from DeliveryAddress d Where d.id = :id and d.userId = :userId")
	void deleteByUser(@Param("id")long id, @Param("userId") long userId);
	
	
	@Query("Select d from DeliveryAddress d Where d.userId = :userId")
	List<DeliveryAddress> getByUserId(@Param("userId") long userId);
}
