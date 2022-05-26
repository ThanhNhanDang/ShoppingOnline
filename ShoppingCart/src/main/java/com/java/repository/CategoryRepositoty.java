package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Category;

@Repository
public interface CategoryRepositoty extends JpaRepository<Category, Long>{

}
