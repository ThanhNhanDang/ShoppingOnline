package com.java.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.java.entity.Feedback;
import com.java.repository.FeedbackRepository;
import com.java.service.FeedbackService;

@Service
@Scope("prototype")
public class FeedbackServiceImpl implements FeedbackService {

	private FeedbackRepository repository;

	public FeedbackServiceImpl(FeedbackRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<Feedback> getAll() {
		return repository.findAllReverse();
	}

	@Override
	public void save(Feedback feedback) {
		if (feedback.getEmail() == "" || feedback.getEmail() == null || feedback.getDes() == ""
				|| feedback.getDes() == null) {
			return;
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		feedback.setTime(dtf.format(now));

		repository.save(feedback);
	}

}
