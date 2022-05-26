package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
	@Query("SELECT f FROM Feedback f ORDER BY id DESC")
	List<Feedback> findAllReverse ();
}
