package com.java.service;

import java.util.List;

import com.java.entity.Feedback;

public interface FeedbackService {
	List<Feedback> getAll ();
	void save (Feedback feedback);

}
