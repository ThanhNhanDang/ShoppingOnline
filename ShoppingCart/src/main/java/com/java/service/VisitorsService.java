package com.java.service;

import java.util.List;

import com.java.entity.Visitors;

public interface VisitorsService {
	void save(Visitors visitors);
	List<Visitors> findAll();
}
