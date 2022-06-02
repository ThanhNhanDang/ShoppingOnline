package com.java.service.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.java.entity.Visitors;
import com.java.repository.VisitorsRepository;
import com.java.service.VisitorsService;

@Scope("prototype")
@Service
public class VisitorsServiceImpl implements VisitorsService{

	private VisitorsRepository repository;
	
	public VisitorsServiceImpl(VisitorsRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(Visitors visitors) {
		this.repository.save(visitors);
	}

	@Override
	public List<Visitors> findAll() {
		return this.repository.findAll();
	}
	
}
