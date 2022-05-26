package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{
	@Query("SELECT p FROM Province p ORDER BY id DESC")
	List<Province> findAllReverse (); 
	@Query("select p from Province p Where CONCAT(p.name, ' ', p.type) LIKE %:key%")
	List<Province> search(@Param("key") String key);
}
