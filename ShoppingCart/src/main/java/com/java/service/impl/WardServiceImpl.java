package com.java.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.entity.Ward;
import com.java.repository.WardRepository;
import com.java.service.WardService;

@Service
@Scope("prototype")
public class WardServiceImpl implements WardService{
	
	private WardRepository repo;
	
	public WardServiceImpl(WardRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public List<Ward> findWardByDistrictId(long districtId) {
		List<Ward> wards = repo.findWardByDistrictId(districtId);
		return wards;
	}

	@Override
	public List<Ward> getAll() {
		List<Ward> wards = repo.findAllReverse();
		return wards;
	}

	@Override
	public List<Ward> search(String key){
		List<Ward> wards = repo.search(key);
		return wards;
	}

	@Transactional
	@Modifying
	@Override
	public List<Ward> delete(Long id) {
		repo.deleteById(id);
		return this.getAll();
	}

	@Transactional
	@Modifying
	@Override
	public Ward save(Ward entity) {
		Ward ward = repo.save(entity);
		return ward;
	}

	@Override
	public Ward find(Long id) {
		return repo.findById(id).get();
	}

	@Transactional
	@Modifying
	@Override
	public void saveAll(List<Ward> entity) {
		repo.saveAll(entity);

	}

	@Transactional
	@Modifying
	@Override
	public void update(Ward entity) {
		Ward ward = repo.findById(entity.getId()).get();
		
		ward.setName(entity.getName());
		ward.setType(entity.getType());
		ward.setDistrictId(entity.getDistrictId());
		repo.save(ward);
	}
}
